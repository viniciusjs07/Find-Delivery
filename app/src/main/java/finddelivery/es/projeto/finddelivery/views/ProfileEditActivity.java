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
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.DrawerListAdapter;
import finddelivery.es.projeto.finddelivery.models.NavItem;
import finddelivery.es.projeto.finddelivery.controllers.UserController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;


public class ProfileEditActivity extends ActionBarActivity implements View.OnClickListener  {

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
    private UserSessionController session;
    private android.support.v7.app.ActionBar actionBar;
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems;
    private ImageView photoUser2;
    private TextView nameUser2;
    private TextView login2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        context = this;
        userController = UserController.getInstance(context);
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

        String name = user.get(UserSessionController.KEY_NAME);
        String login = user.get(UserSessionController.KEY_LOGIN);

        String photoUser = user.get(UserSessionController.KEY_PHOTO);
        byte[] photoUserByte = Base64.decode(photoUser, Base64.DEFAULT);

        Bitmap photoUserBitmap = BitmapFactory.decodeByteArray(photoUserByte, 0, photoUserByte.length);

        imageViewUserProfile2.setImageBitmap(photoUserBitmap);
        imageViewUserProfile2.setImageBitmap(Bitmap.createScaledBitmap(photoUserBitmap, 100, 100, false));

        editTextNameUser2.setText(name);

        actionBar =  getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.icon_menu);

        mNavItems = new ArrayList<NavItem>();
        mNavItems.add(new NavItem("Início", R.drawable.home));
        mNavItems.add(new NavItem("Meu perfil", R.drawable.profileuser));
        mNavItems.add(new NavItem("Meus restaurantes", R.drawable.myrestaurants));
        mNavItems.add(new NavItem("Novo restaurante", R.drawable.addrestaurant));
        mNavItems.add(new NavItem("Sair", R.drawable.logout));
        photoUser2 = (ImageView) findViewById((R.id.photoUser));
        nameUser2 = (TextView) findViewById(R.id.nameUser);
        login2 = (TextView) findViewById(R.id.login);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter drawerAdapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(drawerAdapter);

        String loginUser = user.get(UserSessionController.KEY_LOGIN);

        photoUser2.setImageBitmap(photoUserBitmap);
        photoUser2.setImageBitmap(Bitmap.createScaledBitmap(photoUserBitmap, 50, 50, false));
        nameUser2.setText(name);
        login2.setText(loginUser);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(ProfileEditActivity.this, EstablishmentsActivity.class);
                }
                if (position == 1){
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(ProfileEditActivity.this, UserProfileActivity.class);
                }
                if (position == 2){
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(ProfileEditActivity.this, MyEstablishmentActivity.class);
                }
                if (position == 3){
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(ProfileEditActivity.this, EstablishmentCadastreActivity.class);
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
            if(photo == null){
                String photoUser = user.get(UserSessionController.KEY_PHOTO);
                byte[] photoUserByte = Base64.decode(photoUser, Base64.DEFAULT);
                photo = BitmapFactory.decodeByteArray(photoUserByte, 0, photoUserByte.length);
            }
            photo.compress(Bitmap.CompressFormat.JPEG, 100, b);
            byte[] photo = b.toByteArray();

            if(name != null && name.trim().equals("")){
                showDialog("Nome inválido!");
                editTextNameUser2.setText("");
            } else if (actualPassword != null && !actualPassword.trim().equals("") && !actualPassword.equals(password)){
                showDialog("Senha atual inválida!");
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
                        userController.updateUser(name, login, newPassword, photo);
                        Toast.makeText(getApplicationContext(),
                                "Dados alterados com sucesso!",
                                Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),
                                "Para ver seus dados atualizados faça login novamente!",
                                Toast.LENGTH_LONG).show();
                        setView(ProfileEditActivity.this,
                                UserProfileActivity.class);
                    } else if (actualPassword != null && actualPassword.trim().equals("")) {
                        userController.updateUser(name, login, password, photo);
                        Toast.makeText(getApplicationContext(),
                                "Dados alterados com sucesso!",
                                Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),
                                "Para ver seus dados atualizados faça login novamente!",
                                Toast.LENGTH_LONG).show();
                        setView(ProfileEditActivity.this,
                                UserProfileActivity.class);
                    }
                } else if (name != null && name.equals(actualName)) {
                    if (actualPassword != null && !actualPassword.trim().equals("") && actualPassword.equals(password) && passwordsValid) {
                        userController.updateUser(actualName, login, newPassword, photo);
                        Toast.makeText(getApplicationContext(),
                                "Dados alterados com sucesso!",
                                Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),
                                "Para ver seus dados atualizados faça login novamente!",
                                Toast.LENGTH_LONG).show();
                        setView(ProfileEditActivity.this,
                                UserProfileActivity.class);
                    } else if (actualPassword != null && actualPassword.trim().equals("")) {
                        userController.updateUser(actualName, login, password, photo);
                        setView(ProfileEditActivity.this,
                                UserProfileActivity.class);
                    }
                }
            }
        }
        catch (Exception e){
            showDialog("Erro validando usuário");
            e.printStackTrace();
        }
    }

    public void setView(Context context, Class classe){
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
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
        /*        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

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
                imageViewUserProfile2.setImageBitmap(Bitmap.createScaledBitmap(avatar, 100, 100, false));
                photo = avatar;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CAMERA && resultCode == RESULT_OK) {
            photo = (Bitmap)data.getExtras().get("data");
            imageViewUserProfile2.setImageBitmap(photo);
            imageViewUserProfile2.setImageBitmap(Bitmap.createScaledBitmap(photo, 100, 100, false));

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
                imageViewUserProfile2.setImageBitmap(Bitmap.createScaledBitmap(photo, 100, 100, false));

            }
        }
    }
}