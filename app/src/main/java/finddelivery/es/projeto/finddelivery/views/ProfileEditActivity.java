package finddelivery.es.projeto.finddelivery.views;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.app.AlertDialog;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.UserController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;
import finddelivery.es.projeto.finddelivery.models.User;


public class ProfileEditActivity extends ActionBarActivity implements View.OnClickListener  {

    private Button btnCancelar;
    private Button btnSalvarAteracaoes;
    private ImageButton btnCamera;
    private ImageButton btnGalery;
    private ImageButton btnDelete;
    private ImageView imageViewUserProfile2;
    private EditText editTextNameUser2;
    private EditText editTextActualPassword;
    private EditText editTextNewPassword;
    private EditText editTextNewPasswordConfirm;
    private UserController userController;
    private Context context;
    private AlertDialog.Builder alert;
    private HashMap<String, String> user;
    private static final int RESULT_CAMERA = 111;
    private static final int RESULT_GALERIA = 222;
    private Bitmap photo;
    UserSessionController session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        context = this;
        userController = UserController.getInstance(context);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnSalvarAteracaoes = (Button) findViewById(R.id.btnSalvarAlteracoes);
        btnCamera = (ImageButton) findViewById(R.id.imgCamera);
        btnCamera.setOnClickListener(this);
        btnGalery = (ImageButton) findViewById(R.id.imgGallery);
        btnGalery.setOnClickListener(this);
        btnDelete = (ImageButton) findViewById(R.id.imgDelete);
        btnDelete.setOnClickListener(this);
        imageViewUserProfile2 = (ImageView) findViewById(R.id.imageViewUserProfile2);
        editTextNameUser2 = (EditText) findViewById(R.id.editTextNameUser2);
        editTextActualPassword = (EditText) findViewById(R.id.editTextActualPassword);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        editTextNewPasswordConfirm = (EditText) findViewById(R.id.editTextNewPasswordConfirm);
        session = new  UserSessionController(getApplicationContext());
        user = session.getUserDetails();

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(), Toast.LENGTH_LONG).show();

        if(session.checkLogin()) {
            finish();
        }



        String name = user.get(UserSessionController.KEY_NAME);
        String photoUser = user.get(UserSessionController.KEY_PHOTO);
        byte[] photoUserByte = Base64.decode(photoUser, Base64.DEFAULT);

        Bitmap photoUserBitmap = BitmapFactory.decodeByteArray(photoUserByte, 0, photoUserByte.length);

        imageViewUserProfile2.setImageBitmap(photoUserBitmap);
        editTextNameUser2.setText(name);


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                session.logoutUser(); //Temp
                Intent it = new Intent();
                it.setClass(ProfileEditActivity.this,
                        LoginActivity.class);//Temp
                //  UserProfileActivity.class);
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
        String name = editTextNameUser2.getText().toString();
        String actualPassword = editTextActualPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String newPasswordConfirm = editTextNewPasswordConfirm.getText().toString();

        String actualName = user.get(UserSessionController.KEY_NAME);
        String password = user.get(UserSessionController.KEY_PASSWORD);
        String login = user.get(UserSessionController.KEY_LOGIN);

        boolean passwordsValid = userController.validatesPasswords(newPassword, newPasswordConfirm);

        try {

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 50, b);
            byte[] photo = b.toByteArray();

            if(name != null && name.trim().equals("")){
                showDialog("Nome invalido!");
                editTextNameUser2.setText("");
            } else if (actualPassword != null && !actualPassword.trim().equals("") && !actualPassword.equals(password)){
                showDialog("Senha atual invalida!");
                editTextActualPassword.setText("");
                editTextNewPassword.setText("");
                editTextNewPasswordConfirm.setText("");
            } else if(actualPassword != null && actualPassword.equals(password) && !passwordsValid) {
                showDialog("Senhas nao correspondem!");
                editTextNewPassword.setText("");
                editTextNewPasswordConfirm.setText("");
            } else {
                if (name != null && !name.trim().equals("") && !name.equals(actualName)) {
                    if (actualPassword != null && actualPassword.equals(password) && passwordsValid) {
                        userController.updateData(name, login, newPassword, photo);
                        showDialog("Dados alterados com sucesso!");
                        Intent it = new Intent();
                        it.setClass(ProfileEditActivity.this,
                                UserProfileActivity.class);
                        startActivity(it);
                        finish();
                    } else if (actualPassword != null && actualPassword.trim().equals("")) {
                        userController.updateData(name, login, password, photo);
                        showDialog("Dados alterados com sucesso!");
                        Intent it = new Intent();
                        it.setClass(ProfileEditActivity.this,
                                UserProfileActivity.class);
                        startActivity(it);
                        finish();
                    }

                } else if (name != null && name.equals(actualName)) {
                    if (actualPassword != null && !actualPassword.trim().equals("") && actualPassword.equals(password) && passwordsValid) {
                        userController.updateData(actualName, login, newPassword, photo);
                        showDialog("Dados alterados com sucesso!");
                        Intent it = new Intent();
                        it.setClass(ProfileEditActivity.this,
                                UserProfileActivity.class);
                        startActivity(it);
                        finish();
                    } else if (actualPassword != null && actualPassword.trim().equals("")) {
                        userController.updateData(actualName, login, password, photo);
                        Intent it = new Intent();
                        it.setClass(ProfileEditActivity.this,
                                UserProfileActivity.class);
                        startActivity(it);
                        finish();
                    }
                }
            }
            //userController.updateData(actualName, login, actualPassword, photo);
        }
        catch (Exception e){
            showDialog("Erro validando usuario");
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil_edit, menu);
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

    public void showUserProfile() {
        setContentView(R.layout.activity_user_profile);
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
                imageViewUserProfile2.setImageBitmap(avatar);
                photo = avatar;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CAMERA && resultCode == RESULT_OK) {
            photo = (Bitmap)data.getExtras().get("data");
            imageViewUserProfile2.setImageBitmap(photo);
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
                imageViewUserProfile2.setImageBitmap(photo);
            }
        }
    }
}
