package finddelivery.es.projeto.finddelivery.views;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.*;
import android.content.Intent;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.UserController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;
import finddelivery.es.projeto.finddelivery.models.User;


public class UserCadastreActivity extends ActionBarActivity implements View.OnClickListener {

    private ImageView cadastrePhotoImageView;
    private EditText cadastreNameEditText;
    private EditText cadastreLoginEditText;
    private EditText cadastrePasswordEditText;
    private EditText cadastrePasswordConfirmEditText;
    private Button btnRegister;
    private Button btnCancel;
    private ImageButton btnCamera;
    private ImageButton btnGalery;
    private ImageButton btnDelete;
    private Context context;
    private UserController userController;
    private AlertDialog.Builder alert;
    private static final int RESULT_CAMERA = 111;
    private static final int RESULT_GALERIA = 222;
    private Bitmap photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cadastre);
        Bitmap avatar = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        photo = avatar;

        context = this;
        userController = UserController.getInstance(context);
        cadastrePhotoImageView = (ImageView) findViewById(R.id.cadastrePhotoImageView);
        btnCamera = (ImageButton) findViewById(R.id.imgCamera);
        btnCamera.setOnClickListener(this);
        btnGalery = (ImageButton) findViewById(R.id.imgGallery);
        btnGalery.setOnClickListener(this);
        btnDelete = (ImageButton) findViewById(R.id.imgDelete);
        btnDelete.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        cadastreNameEditText = (EditText) findViewById(R.id.cadastreNameEditText);
        cadastreLoginEditText = (EditText) findViewById(R.id.cadastreLoginEditText);
        cadastrePasswordEditText = (EditText) findViewById(R.id.cadastrePasswordEditText);
        cadastrePasswordConfirmEditText = (EditText) findViewById(R.id.cadastrePasswordConfirmEditText);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(UserCadastreActivity.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    public void showDialog(String mensagem) {
        alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", null);
        alert.setMessage(mensagem);
        alert.create().show();
    }

    public void validates(View view) throws Exception {
        String name = cadastreNameEditText.getText().toString();
        String login = cadastreLoginEditText.getText().toString();
        String password = cadastrePasswordEditText.getText().toString();
        String passwordConfirm = cadastrePasswordConfirmEditText.getText().toString();

        boolean passwordsValid = userController.validatesPasswords(password, passwordConfirm);
        boolean loginValid = userController.validatesUserName(login);
        try {
            if (loginValid){
                if (!passwordsValid) {
                    showDialog("Senha inválida!");
                    cadastrePasswordEditText.setText("");
                    cadastrePasswordConfirmEditText.setText("");

                } else {
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, b);
                    byte[] photo = b.toByteArray();

                    showDialog("Cadastro realizado com sucesso!");
                    User user = new User(name, login, password, photo);

                    userController.insert(user);

                    Intent it = new Intent();
                    it.setClass(UserCadastreActivity.this,
                            LoginActivity.class);
                    startActivity(it);
                    finish();
                }
            } else if (!loginValid) {
                showDialog("Login inválido!");
                cadastreLoginEditText.setText("");
                cadastrePasswordEditText.setText("");
                cadastrePasswordConfirmEditText.setText("");
            }
        }
        catch (Exception e){
            showDialog("Erro validando usuário");
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.imgCamera:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_CAMERA);
                break;
            case R.id.imgGallery:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_GALERIA);
                break;
            case R.id.imgDelete:
                Bitmap avatar = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
                cadastrePhotoImageView.setImageBitmap(avatar);
                cadastrePhotoImageView.setImageBitmap(Bitmap.createScaledBitmap(avatar, 100, 100, false));
                break;
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CAMERA && resultCode == RESULT_OK) {

            photo = (Bitmap)data.getExtras().get("data");
            cadastrePhotoImageView.setImageBitmap(photo);
            cadastrePhotoImageView.setImageBitmap(Bitmap.createScaledBitmap(photo, 100, 100, false));


        } else if (requestCode == RESULT_GALERIA && resultCode == RESULT_OK) {
            //Uri (local da tabela do banco de dados) do dado (no caso, da imagem)
            Uri imageUri = data.getData();
            //Se tratantando de banco de dados, devemos selecionar exatamente qual coluna queremos
            String[] colunaArquivo = {MediaStore.Images.Media.DATA};
            //Fazemos um select simples na tabela trazendo apenas a coluna que selecionamos
            Cursor cursor = getContentResolver().query(imageUri, colunaArquivo, null, null, null);
            //Movemos nosso cursor para o primeiro resultado do select
            cursor.moveToFirst();
            //Recuperamos o indice de qual coluna da tabela estamos referenciando
            int columnIndex = cursor.getColumnIndex(colunaArquivo[0]);
            //O campo da tabela guarda o caminho da imagem. Recuperamos tal caminho
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //Pegamos o arquivo do caminho que recuperamos e decodificamos para imagem
            photo = BitmapFactory.decodeFile(picturePath.toString());
            //Se o arquivo nao estiver nulo (e for uma imagem e nao um video por exemplo)
            if (photo != null) {
                cadastrePhotoImageView.setImageBitmap(photo);
                cadastrePhotoImageView.setImageBitmap(Bitmap.createScaledBitmap(photo, 100, 100, false));
            }
        }
    }

}
