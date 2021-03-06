package finddelivery.es.projeto.finddelivery.views;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.DrawerListAdapter;
import finddelivery.es.projeto.finddelivery.models.Mask;
import finddelivery.es.projeto.finddelivery.models.NavItem;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;
import finddelivery.es.projeto.finddelivery.models.Establishment;

public class EstablishmentEditActivity extends ActionBarActivity implements View.OnClickListener {

    private Spinner sp;
    private List<String> specialityTypes;
    private EditText editTextHorario;
    private EditText editTextAddress;
    private TextView textViewtEstablishmentName;
    private EditText editTextPhoneOne;
    private EditText editTextPhoneTwo;
    private Establishment establishment;
    private AlertDialog.Builder alert;
    private ImageView logoEstablishmentImageView;
    private ImageButton btnCamera;
    private ImageButton btnGalery;
    private ImageButton btnDelete;
    private Bitmap establishmentLogo;
    private static final int RESULT_CAMERA = 111;
    private static final int RESULT_GALERIA = 222;
    private UserSessionController session;
    private EstablishmentController establishmentController;
    private Context context;
    private HashMap<String, String> user;
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems;
    private ImageView photoUser;
    private TextView nameUser;
    private TextView login;
    private android.support.v7.app.ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_edit);

        context = this;
        establishmentController = EstablishmentController.getInstance(context);

        session = new  UserSessionController(getApplicationContext());
        user = session.getUserDetails();

        textViewtEstablishmentName = (TextView) findViewById(R.id.textViewtEstablishmentName);
        editTextHorario = (EditText) findViewById(R.id.editTextHorario);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextPhoneOne = (EditText) findViewById(R.id.editTextPhoneOne);
        editTextPhoneTwo = (EditText) findViewById(R.id.editTextPhoneTwo);
        editTextPhoneOne.addTextChangedListener(Mask.insert("(###)####-####", editTextPhoneOne));
        editTextPhoneTwo.addTextChangedListener(Mask.insert("(###)####-####", editTextPhoneTwo));

        logoEstablishmentImageView = (ImageView)findViewById(R.id.logoEstablishmentImageView);
        btnCamera = (ImageButton)findViewById(R.id.imgCamera);
        btnCamera.setOnClickListener(this);
        btnGalery = (ImageButton)findViewById(R.id.imgGallery);
        btnGalery.setOnClickListener(this);
        btnDelete = (ImageButton)findViewById(R.id.imgDelete);
        btnDelete.setOnClickListener(this);

        actionBar =  getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.icon_menu);

        mNavItems = new ArrayList<NavItem>();
        mNavItems.add(new NavItem("Início", R.drawable.home));
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
                    setView(EstablishmentEditActivity.this, EstablishmentsActivity.class);
                }
                if (position == 1){
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(EstablishmentEditActivity.this, UserProfileActivity.class);
                }
                if (position == 2){
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(EstablishmentEditActivity.this, MyEstablishmentActivity.class);
                }
                if (position == 3){
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(EstablishmentEditActivity.this, EstablishmentCadastreActivity.class);
                }
                if (position == 4){
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    session.logoutUser();
                    Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                    ComponentName cn = it.getComponent();
                    Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                    startActivity(mainIntent);
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

        Intent it = getIntent();
        establishment = (Establishment) it.getSerializableExtra("ESTABLISHMENTDETAILS");

        byte[] logoEstablishment = establishment.getPhoto();
        Bitmap logoBitmap = BitmapFactory.decodeByteArray(logoEstablishment, 0, logoEstablishment.length);

        textViewtEstablishmentName.setText(establishment.getName());
        logoEstablishmentImageView.setImageBitmap(logoBitmap);
        logoEstablishmentImageView.setImageBitmap(Bitmap.createScaledBitmap(logoBitmap, 100, 100, false));
        editTextAddress.setText(establishment.getAddress());
        editTextHorario.setText(establishment.getBusinessHour());
        editTextPhoneOne.setText(establishment.getPhone1());
        editTextPhoneTwo.setText(establishment.getPhone2());
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
        getMenuInflater().inflate(R.menu.menu_establishment_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

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
            default:
                Bitmap photoDefaultEstablishment = BitmapFactory.decodeResource(getResources(), R.mipmap.photodefault);
                logoEstablishmentImageView.setImageBitmap(photoDefaultEstablishment);
                logoEstablishmentImageView.setImageBitmap(Bitmap.createScaledBitmap(photoDefaultEstablishment, 100, 100, false));
                establishmentLogo = photoDefaultEstablishment;
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

    public void confirmEditions(View view) throws Exception {
        String name = textViewtEstablishmentName.getText().toString();
        String address = editTextAddress.getText().toString();
        String businessHour = editTextHorario.getText().toString();
        String specialityType = sp.getSelectedItem().toString();
        String phone1 = editTextPhoneOne.getText().toString();
        String phone2 = editTextPhoneTwo.getText().toString();

        boolean validateAddress = establishmentController.validatesEstablishmentAddress(address);
        boolean validateWorkHour = establishmentController.validatesEstablishmentBusinesseHour(businessHour);
        boolean validatePhoneOne = establishmentController.validateEstablishmentPhone(phone1);

        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            if (establishmentLogo == null) {
                byte[] photo = (establishmentController.getEstablishment(establishment.getName())).getPhoto();
                establishmentLogo = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            }
            establishmentLogo.compress(Bitmap.CompressFormat.JPEG, 100, b);
            byte[] establishmentLogo = b.toByteArray();

            if (!validateAddress) {
                showDialog("Endereço inválido!");
                editTextAddress.setText("");

            } else if (!validateWorkHour) {
                showDialog("Horário inválido!");
                editTextHorario.setText("");

            } else if (!validatePhoneOne) {
                showDialog("Telefone 1 inválido!");
                editTextPhoneOne.setText("");

            } else {
                String login = user.get(UserSessionController.KEY_LOGIN);
                establishmentController.updateEstablishment(name, address, businessHour, specialityType, phone1, phone2, establishmentLogo, login);
                Toast.makeText(context, "Estabelecimento atualizado com sucesso!", Toast.LENGTH_LONG).show();
                Intent it = new Intent();
                it.setClass(EstablishmentEditActivity.this,
                        EstablishmentDetailsActivity.class);
                it.putExtra("ESTABLISHMENT", establishment);
                startActivity(it);

            }
        }catch (Exception e){
            showDialog("Erro validando estabelecimento");
            e.printStackTrace();
        }
    }

    public void showDialog(String mensagem) {
        alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", null);
        alert.setMessage(mensagem);
        alert.create().show();
    }

    public void setView(Context context, Class classe){
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
    }
}
