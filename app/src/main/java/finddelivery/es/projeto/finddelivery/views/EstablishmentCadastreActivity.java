package finddelivery.es.projeto.finddelivery.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.models.Establishment;

public class EstablishmentCadastreActivity extends ActionBarActivity implements View.OnClickListener {

    private Spinner sp;
    private List<String> specialityTypes;
    private EditText editTextHorario;
    private EditText editTextAddress;
    private EditText editTextEstablishmentName;
    private EditText editTextPhoneOne;
    private EditText editTextPhoneTwo;

    private ImageView logoEstablishmentImageView;
    private ImageButton btnCamera;
    private ImageButton btnGalery;
    private ImageButton btnDelete;

    private static final int RESULT_CAMERA = 111;
    private static final int RESULT_GALERIA = 222;

    private EstablishmentController establishmentController;
    private Context context;
    private AlertDialog.Builder alert;
    private Bitmap establishmentLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_cadastre);
        establishmentController = EstablishmentController.getInstance(context);

        Bitmap photoDefault = BitmapFactory.decodeResource(getResources(), R.mipmap.photodefault);
        establishmentLogo = photoDefault;

        logoEstablishmentImageView = (ImageView)findViewById(R.id.logoEstablishmentImageView);
        btnCamera = (ImageButton)findViewById(R.id.imgCamera);
        btnCamera.setOnClickListener(this);
        btnGalery = (ImageButton)findViewById(R.id.imgGallery);
        btnGalery.setOnClickListener(this);
        btnDelete = (ImageButton)findViewById(R.id.imgDelete);
        btnDelete.setOnClickListener(this);

        editTextHorario = (EditText) findViewById(R.id.editTextHorario);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextEstablishmentName = (EditText) findViewById(R.id.editTextEstablishmentName);
        editTextPhoneOne = (EditText) findViewById(R.id.editTextPhoneOne);
        editTextPhoneTwo = (EditText) findViewById(R.id.editTextPhoneTwo);


        specialityTypes = new ArrayList<String>();
        addTypes();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, specialityTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp = (Spinner) findViewById(R.id.spinnerTipoDeCozinha);
        sp.setAdapter(adapter);


    }

    private void addTypes() {
        specialityTypes.add("Comida Brasileira");
        specialityTypes.add("Comida Mexicana");
        specialityTypes.add("Comida Japonesa");
        specialityTypes.add("Comida Chinesa");
        specialityTypes.add("Comida Italiana");
        specialityTypes.add("Comida Variada");
        specialityTypes.add("Comida Saudavel");
        specialityTypes.add("Lanches");
        specialityTypes.add("Pizza");
        specialityTypes.add("Doces");
        specialityTypes.add("Salgados");
        specialityTypes.add("Frutos do Mar");
        specialityTypes.add("Cafe");
        specialityTypes.add("Carnes");
        specialityTypes.add("Bebidas");
        specialityTypes.add("Saladas");
        specialityTypes.add("Marmitas");
        specialityTypes.add("Massas");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_establishment_cadastre, menu);
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

    public void showDialog(String mensagem) {
        alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", null);
        alert.setMessage(mensagem);
        alert.create().show();
    }

    public void submitEstablishment(View view) throws Exception{

        String name = editTextEstablishmentName.getText().toString();
        String address = editTextAddress.getText().toString();
        String specialityType = sp.getSelectedItem().toString();
        String workHour = editTextHorario.getText().toString();
        String phone1 = editTextPhoneOne.getText().toString();
        String phone2 = editTextPhoneTwo.getText().toString();

        boolean validateName = establishmentController.validatesEstablishmentName(name);
        boolean validateAddress = establishmentController.validatesEstablishmentAddress(address);
        boolean validateWorkHour = establishmentController.validatesEstablishmentBusinesseHour(workHour);
        boolean validatePhoneOne = establishmentController.validateEstablishmentPhone(phone1);
        boolean validatePhoneTwo = establishmentController.validateEstablishmentPhone(phone2);

        try {
            if (validateName && validateAddress && validateWorkHour && validatePhoneOne && validatePhoneTwo){

                ByteArrayOutputStream b = new ByteArrayOutputStream();
                establishmentLogo.compress(Bitmap.CompressFormat.JPEG, 50, b);
                byte[] establishmentLogo = b.toByteArray();

                showDialog("Estabelecimento cadastrado com sucesso!");
                Establishment establishment = new Establishment(name, address, specialityType, workHour, phone1, phone2, establishmentLogo);

                establishmentController.insert(establishment);

                Intent it = new Intent();
                it.setClass(EstablishmentCadastreActivity.this,
                        EstablishmentsActivity.class);
                startActivity(it);
                finish();

            }
        }
        catch (Exception e){
            showDialog("Erro ao cadastrar estabelecimento!");
            e.printStackTrace();
        }

        /*
        Intent i = new Intent(EstablishmentCadastreActivity.this,EstablishmentsActivity.class);

        i.putExtra("name",name.getText().toString());
        i.putExtra("addres",addres.getText().toString());
        i.putExtra("fone1",fone1.getText().toString());
        i.putExtra("fone2",fone2.getText().toString());
        startActivity(i);
        */

        //EstablishmentController.getInstance().addEstablishment(...);

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
                Bitmap photoDefaultEstablishment = BitmapFactory.decodeResource(getResources(), R.mipmap.photodefault);
                logoEstablishmentImageView.setImageBitmap(photoDefaultEstablishment);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CAMERA && resultCode == RESULT_OK) {
            establishmentLogo = (Bitmap)data.getExtras().get("data");
            logoEstablishmentImageView.setImageBitmap(establishmentLogo);
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
            establishmentLogo = BitmapFactory.decodeFile(picturePath.toString());
            //Se o arquivo nao estiver nulo (e for uma imagem e nao um video por exemplo)
            if (establishmentLogo != null) {
                logoEstablishmentImageView.setImageBitmap(establishmentLogo);
            }
        }
    }
}
