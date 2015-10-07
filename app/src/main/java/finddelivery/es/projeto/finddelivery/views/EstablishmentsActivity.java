package finddelivery.es.projeto.finddelivery.views;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.DrawerListAdapter;
import finddelivery.es.projeto.finddelivery.adapter.ListMyEstablishmentAdapter;
import finddelivery.es.projeto.finddelivery.adapter.NavItem;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;

import finddelivery.es.projeto.finddelivery.models.Establishment;


public class EstablishmentsActivity extends ActionBarActivity {

    private Button btnAdvancedSearch;
    private ListView listViewEstablishments;
    private ListMyEstablishmentAdapter adapter;
    private Context context;
    EstablishmentController establishmentController;

    private AlertDialog.Builder alert;

    UserSessionController session;

    private ImageView photoUser;
    private TextView nameUser;
    private TextView login;
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ActionBar actionBar;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishments);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);

        actionBar.setIcon(R.mipmap.ic_launcher);

        context = this;
        establishmentController = EstablishmentController.getInstance(context);
        session = new UserSessionController(getApplicationContext());

        btnAdvancedSearch = (Button) findViewById(R.id.btnAdvancedSearch);
        listViewEstablishments = (ListView) findViewById(R.id.listViewEstablishments);

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();


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
                        it.setClass(EstablishmentsActivity.this,
                                UserProfileActivity.class);
                        startActivity(it);
                    }
                    if (position == 1) {
                        mDrawerLayout.closeDrawer(mDrawerPane);
                        Intent it = new Intent();
                        it.setClass(EstablishmentsActivity.this,
                                MyEstablishmentActivity.class);
                        startActivity(it);
                    }
                    if (position == 2) {
                        mDrawerLayout.closeDrawer(mDrawerPane);
                        Intent it = new Intent();
                        it.setClass(EstablishmentsActivity.this,
                                EstablishmentCadastreActivity.class);
                        startActivity(it);
                    }
                    if (position == 3) {
                        mDrawerLayout.closeDrawer(mDrawerPane);
                        session.logoutUser();
                        Intent it = new Intent();
                        it.setClass(EstablishmentsActivity.this,
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

            listViewEstablishments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Establishment item = adapter.getItem(position);

                    Intent intent = new Intent(EstablishmentsActivity.this, EstablishmentDetails.class);
                    intent.putExtra("ESTABLISHMENT", item);

                    startActivity(intent);
                }
            });


            btnAdvancedSearch.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent it = new Intent();
                    it.setClass(EstablishmentsActivity.this,
                            FindEstablishmentActivity.class);
                    startActivity(it);
                }
            });



        }


    public void showDialog(String mensagem) {
        alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", null);
        alert.setMessage(mensagem);
        alert.create().show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (!establishmentController.isSearchAdvanced) {
                List<Establishment> establishmentsList = establishmentController.findAll();
                adapter = new ListMyEstablishmentAdapter(this, establishmentsList);
            }else {
                if (establishmentController.isIsSearchAdvancedByName) {
                    List<Establishment> establishmentsList = establishmentController.listByName();
                    if(establishmentsList.size() == 0){
                        showDialog("Restaurante não encontrado!");
                        Intent it = new Intent();
                        it.setClass(EstablishmentsActivity.this,
                                FindEstablishmentActivity.class);
                        startActivity(it);
                    }else {
                        adapter = new ListMyEstablishmentAdapter(this, establishmentsList);
                    }
                }else{
                    List<Establishment> establishmentsList = establishmentController.listBySpeciality();
                    if(establishmentsList.size() == 0){
                        showDialog("Restaurante não encontrado!");
                        Intent it = new Intent();
                        it.setClass(EstablishmentsActivity.this,
                                FindEstablishmentActivity.class);
                        startActivity(it);
                    }else {
                        adapter = new ListMyEstablishmentAdapter(this, establishmentsList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listViewEstablishments.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_establishments, menu);
        return true;



       // return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.getItemId();

                Intent intent = new Intent(this, UserProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                mDrawerLayout.openDrawer(mDrawerLayout);

                return super.onOptionsItemSelected(item);

    }


}