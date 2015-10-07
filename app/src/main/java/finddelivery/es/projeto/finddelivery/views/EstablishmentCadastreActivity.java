package finddelivery.es.projeto.finddelivery.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.DrawerListAdapter;
import finddelivery.es.projeto.finddelivery.adapter.NavItem;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;
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
    UserSessionController session;
    private ActionBar actionBar;

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    private ImageView photoUser;
    private TextView nameUser;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_cadastre);


        actionBar =  getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        context = this;
        establishmentController = EstablishmentController.getInstance(context);

        Bitmap photoDefault = BitmapFactory.decodeResource(getResources(), R.mipmap.photodefault);
        establishmentLogo = photoDefault;
        session = new  UserSessionController(getApplicationContext());


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
        editTextPhoneOne.addTextChangedListener(Mask.insert("(###)####-####", editTextPhoneOne));
        editTextPhoneTwo.addTextChangedListener(Mask.insert("(###)####-####", editTextPhoneTwo));


        mNavItems.add(new NavItem("Meu perfil", R.drawable.profileuser));
        mNavItems.add(new NavItem("Meus restaurantes", R.drawable.myrestaurants));
        mNavItems.add(new NavItem("Novo restaurante", R.drawable.addrestaurant));
        mNavItems.add(new NavItem("Sair", R.drawable.logout));
        photoUser = (ImageView) findViewById((R.id.photoUser));
        nameUser = (TextView) findViewById(R.id.nameUser);
        login = (TextView) findViewById(R.id.login);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter drawerAdapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(drawerAdapter);


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(EstablishmentCadastreActivity.this,
                            UserProfileActivity.class);
                    startActivity(it);
                }
                if (position == 1) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(EstablishmentCadastreActivity.this,
                            MyEstablishmentActivity.class);
                    startActivity(it);
                }
                if (position == 2) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(EstablishmentCadastreActivity.this,
                            EstablishmentCadastreActivity.class);
                    startActivity(it);
                }
                if (position == 3) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    session.logoutUser();
                    Intent it = new Intent();
                    it.setClass(EstablishmentCadastreActivity.this,
                            LoginActivity.class);
                    startActivity(it);
                }
            }
        });

        Map<String, String> user = session.getUserDetails();

        String photo = user.get(UserSessionController.KEY_PHOTO);
        String name = user.get(UserSessionController.KEY_NAME);
        String loginUser = user.get(UserSessionController.KEY_LOGIN);


        byte[] photoUserByte = Base64.decode(photo, Base64.DEFAULT);

        Bitmap photoUserBitmap = BitmapFactory.decodeByteArray(photoUserByte, 0, photoUserByte.length);

        photoUser.setImageBitmap(photoUserBitmap);
        photoUser.setImageBitmap(Bitmap.createScaledBitmap(photoUserBitmap, 50, 50, false));
        nameUser.setText(name);
        login.setText(loginUser);

        specialityTypes = new ArrayList<String>();
        addTypes();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, specialityTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp = (Spinner) findViewById(R.id.spinnerTipoDeCozinha);
        sp.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addTypes() {
        specialityTypes.add("Bebidas");
        specialityTypes.add("Café");
        specialityTypes.add("Carnes");
        specialityTypes.add("Comida Brasileira");
        specialityTypes.add("Comida Chinesa");
        specialityTypes.add("Comida Italiana");
        specialityTypes.add("Comida Japonesa");
        specialityTypes.add("Comida Mexicana");
        specialityTypes.add("Comida Saudável");
        specialityTypes.add("Comida Variada");
        specialityTypes.add("Doces");
        specialityTypes.add("Frutos do Mar");
        specialityTypes.add("Lanches");
        specialityTypes.add("Marmitas");
        specialityTypes.add("Massas");
        specialityTypes.add("Pizza");
        specialityTypes.add("Saladas");
        specialityTypes.add("Salgados");
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
        alert.setPositiveButton(null, null);
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

        HashMap<String, String> user = session.getUserDetails();
        String idUser = user.get(UserSessionController.KEY_LOGIN);


        try {
            if (validateName && validateAddress && validateWorkHour && (validatePhoneOne && validatePhoneTwo || validatePhoneOne)){

                ByteArrayOutputStream b = new ByteArrayOutputStream();
                establishmentLogo.compress(Bitmap.CompressFormat.JPEG, 50, b);
                byte[] establishmentLogo = b.toByteArray();

                Establishment establishment = new Establishment(name, address, workHour, specialityType, phone1, phone2, establishmentLogo);
                establishmentController.insert(establishment, idUser);
                showDialog("Estabelecimento cadastrado com sucesso!");

                Intent it = new Intent();
                it.setClass(EstablishmentCadastreActivity.this,
                        EstablishmentsActivity.class);
                startActivity(it);
                finish();

            } else if (!validateName) {
                showDialog("Nome inválido!");
                editTextEstablishmentName.setText("");
            } else if (!validateAddress) {
                showDialog("Endereço inválido!");
                editTextAddress.setText("");
            } else if (!validateWorkHour){
                showDialog("Horário inválido!");
                editTextHorario.setText("");
            } else if (!validatePhoneOne) {
                showDialog("Telefone 1 inválido!");
                editTextPhoneOne.setText("");
            }
        }
        catch (Exception e){
            showDialog("Erro ao cadastrar estabelecimento!");
            e.printStackTrace();
        }
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
                logoEstablishmentImageView.setImageBitmap(Bitmap.createScaledBitmap(photoDefaultEstablishment, 100, 100, false));

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CAMERA && resultCode == RESULT_OK) {
            establishmentLogo = (Bitmap)data.getExtras().get("data");
            logoEstablishmentImageView.setImageBitmap(establishmentLogo);
            logoEstablishmentImageView.setImageBitmap(Bitmap.createScaledBitmap(establishmentLogo, 100, 100, false));

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
                logoEstablishmentImageView.setImageBitmap(Bitmap.createScaledBitmap(establishmentLogo, 100, 100, false));
            }
        }
    }
}
