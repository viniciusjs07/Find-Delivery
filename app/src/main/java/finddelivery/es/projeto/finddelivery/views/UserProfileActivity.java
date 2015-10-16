package finddelivery.es.projeto.finddelivery.views;

import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.*;
import android.widget.*;
import android.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashMap;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.DrawerListAdapter;
import finddelivery.es.projeto.finddelivery.adapter.NavItem;
import finddelivery.es.projeto.finddelivery.controllers.UserController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;


public class UserProfileActivity extends ActionBarActivity  {

    private AlertDialog alertDeleteAccount;
    private Button btnAlterarDados;
    private Button btnExcluirConta;
    private ImageView imageViewUserProfile;
    private TextView editTextNameUser;
    private TextView editTextLoginUser;
    private HashMap<String, String> user;
    private UserController userController;
    private Context context;
    UserSessionController session;
    private ActionBar actionBar;

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private ImageView photoUser2;
    private TextView nameUser2;
    private TextView login2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        imageViewUserProfile = (ImageView) findViewById((R.id.imageViewUserProfile));
        editTextNameUser = (TextView) findViewById(R.id.editTextNameUser);
        editTextLoginUser = (TextView) findViewById(R.id.editTextLoginUser);
        btnAlterarDados = (Button) findViewById(R.id.btnAlterarDados);
        btnExcluirConta = (Button) findViewById(R.id.btnExcluirConta);

        context = this;
        userController = UserController.getInstance(context);
        session = new  UserSessionController(getApplicationContext());

        user = session.getUserDetails();
        String name = user.get(UserSessionController.KEY_NAME);
        String login = user.get(UserSessionController.KEY_LOGIN);

        String photoUser = user.get(UserSessionController.KEY_PHOTO);
        byte[] photoUserByte = Base64.decode(photoUser, Base64.DEFAULT);

        Bitmap photoUserBitmap = BitmapFactory.decodeByteArray(photoUserByte,0, photoUserByte.length);

        imageViewUserProfile.setImageBitmap(photoUserBitmap);
        imageViewUserProfile.setImageBitmap(Bitmap.createScaledBitmap(photoUserBitmap, 100, 100, false));

        mNavItems.add(new NavItem("Início", R.drawable.home));
        mNavItems.add(new NavItem("Meu perfil", R.drawable.profileuser));
        mNavItems.add(new NavItem("Meus restaurantes", R.drawable.myrestaurants));
        mNavItems.add(new NavItem("Novo restaurante", R.drawable.addrestaurant));
        mNavItems.add(new NavItem("Sair", R.drawable.logout));
        photoUser2 = (ImageView) findViewById((R.id.photoUser));
        nameUser2 = (TextView) findViewById(R.id.nameUser);
        login2 = (TextView) findViewById(R.id.login);

        photoUser2.setImageBitmap(photoUserBitmap);
        photoUser2.setImageBitmap(Bitmap.createScaledBitmap(photoUserBitmap, 50, 50, false));
        nameUser2.setText(name);
        login2.setText(login);

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
                    it.setClass(UserProfileActivity.this,
                            EstablishmentsActivity.class);
                    startActivity(it);
                }
                if (position == 1) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(UserProfileActivity.this,
                            UserProfileActivity.class);
                    startActivity(it);
                }
                if (position == 2) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(UserProfileActivity.this,
                            MyEstablishmentActivity.class);
                    startActivity(it);
                }
                if (position == 3) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(UserProfileActivity.this,
                            EstablishmentCadastreActivity.class);
                    startActivity(it);
                }
                if (position == 4) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    session.logoutUser();
                    Intent it = new Intent();
                    it.setClass(UserProfileActivity.this,
                            LoginActivity.class);
                    startActivity(it);
                }
            }
        });

        editTextNameUser.setText(name);
        editTextLoginUser.setText(login);

        btnAlterarDados.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent it = new Intent();
                it.setClass(UserProfileActivity.this,
                        ProfileEditActivity.class);
                startActivity(it);
                finish();
            }
        });


        btnExcluirConta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder deleteAccount = new AlertDialog.Builder(UserProfileActivity.this);
                deleteAccount.setMessage(R.string.dialog_deleteAccount)
                        .setPositiveButton(R.string.dialog_positiveAwswer, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    deteleProfile(user.get(UserSessionController.KEY_LOGIN));
                                    session.logoutUser();

                                    Toast.makeText(getApplicationContext(), R.string.dialog_accountDeleted, Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent();
                                    it.setClass(UserProfileActivity.this, LoginActivity.class);
                                    startActivity(it);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton(R.string.dialog_negativeAwswer, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                alertDeleteAccount = deleteAccount.create();
                alertDeleteAccount.show();

            }
        });

    }

    public void deteleProfile(String login) throws Exception {
        userController.delete(login);
        Toast.makeText(getApplicationContext(),
                "Usuário deletado!",
                Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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

}