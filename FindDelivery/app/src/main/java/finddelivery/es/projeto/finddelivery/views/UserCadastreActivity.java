package finddelivery.es.projeto.finddelivery.views;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.*;
import android.content.Intent;
import finddelivery.es.projeto.finddelivery.R;


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

    private static final int RESULT_CAMERA = 111;
    private static final int RESULT_GALERIA = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cadastre);

        cadastrePhotoImageView = (ImageView)findViewById(R.id.cadastrePhotoImageView);
        btnCamera = (ImageButton)findViewById(R.id.camera);
        btnCamera.setOnClickListener(this);
        btnGalery = (ImageButton)findViewById(R.id.galeria);
        btnGalery.setOnClickListener(this);
        btnDelete = (ImageButton)findViewById(R.id.excluir);
        btnDelete.setOnClickListener(this);
        cadastreNameEditText = (EditText)findViewById(R.id.cadastreNameEditText);
        cadastreLoginEditText = (EditText)findViewById(R.id.cadastreLoginEditText);
        cadastrePasswordEditText = (EditText)findViewById(R.id.cadastrePasswordEditText);
        cadastrePasswordConfirmEditText = (EditText)findViewById(R.id.cadastrePasswordConfirmEditText);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnCancel = (Button)findViewById(R.id.btnCancel);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(UserCadastreActivity.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.dialog_registerOK, Toast.LENGTH_SHORT).show();
                Intent it = new Intent();
                it.setClass(UserCadastreActivity.this,
                        LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
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
            case R.id.camera:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_CAMERA);
                break;
            case R.id.galeria:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_GALERIA);
                break;
            case R.id.excluir:
                cadastrePhotoImageView.setImageBitmap(null);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CAMERA && resultCode == RESULT_OK) {
            Bitmap foto = (Bitmap)data.getExtras().get("data");
            cadastrePhotoImageView.setImageBitmap(foto);
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
            Bitmap foto = BitmapFactory.decodeFile(picturePath.toString());
            //Se o arquivo nao estiver nulo (e for uma imagem e nao um video por exemplo)
            if (foto != null) {
                cadastrePhotoImageView.setImageBitmap(foto);
            }
        }

    }
}
