package finddelivery.es.projeto.finddelivery.views;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import finddelivery.es.projeto.finddelivery.R;

public class EstablishmentCadastreActivity extends ActionBarActivity implements View.OnClickListener {

    private Spinner sp;
    private List<String> specialityTypes;

    private ImageView logoEstablishmentImageView;
    private ImageButton btnCamera;
    private ImageButton btnGalery;
    private ImageButton btnDelete;

    private static final int RESULT_CAMERA = 111;
    private static final int RESULT_GALERIA = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_cadastre);

        logoEstablishmentImageView = (ImageView)findViewById(R.id.logoEstablishmentImageView);
        btnCamera = (ImageButton)findViewById(R.id.imgCamera);
        btnCamera.setOnClickListener(this);
        btnGalery = (ImageButton)findViewById(R.id.imgGallery);
        btnGalery.setOnClickListener(this);
        btnDelete = (ImageButton)findViewById(R.id.imgDelete);
        btnDelete.setOnClickListener(this);

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
                logoEstablishmentImageView.setImageBitmap(null);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CAMERA && resultCode == RESULT_OK) {
            Bitmap foto = (Bitmap)data.getExtras().get("data");
            logoEstablishmentImageView.setImageBitmap(foto);
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
                logoEstablishmentImageView.setImageBitmap(foto);
            }
        }
    }
}
