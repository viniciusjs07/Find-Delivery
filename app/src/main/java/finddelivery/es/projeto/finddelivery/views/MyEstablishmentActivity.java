package finddelivery.es.projeto.finddelivery.views;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.DrawerListAdapter;
import finddelivery.es.projeto.finddelivery.adapter.ListMyEstablishmentAdapter;
import finddelivery.es.projeto.finddelivery.adapter.NavItem;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;
import finddelivery.es.projeto.finddelivery.models.Establishment;


public class MyEstablishmentActivity extends ActionBarActivity {

    private ListView listViewMyEstablishments;
    private ListMyEstablishmentAdapter adapter;
    private Context context;
    EstablishmentController establishmentController;
    private android.support.v7.app.ActionBar actionBar;

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    UserSessionController session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_establishment);

        actionBar =  getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        context = this;
        establishmentController = EstablishmentController.getInstance(context);
        session = new  UserSessionController(getApplicationContext());


        mNavItems.add(new NavItem("Meu perfil", R.drawable.profileuser));
        mNavItems.add(new NavItem("Meus restaurantes", R.drawable.myrestaurants));
        mNavItems.add(new NavItem("Novo restaurante", R.drawable.addrestaurant));
        mNavItems.add(new NavItem("Sair", R.drawable.logout));

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
                    it.setClass(MyEstablishmentActivity.this,
                            UserProfileActivity.class);
                    startActivity(it);
                }
                if (position == 1) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(MyEstablishmentActivity.this,
                            MyEstablishmentActivity.class);
                    startActivity(it);
                }
                if (position == 2) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(MyEstablishmentActivity.this,
                            EstablishmentCadastreActivity.class);
                    startActivity(it);
                }
                if (position == 3) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    session.logoutUser();
                    Intent it = new Intent();
                    it.setClass(MyEstablishmentActivity.this,
                            LoginActivity.class);
                    startActivity(it);
                }
            }
        });

        listViewMyEstablishments = (ListView) findViewById(R.id.listViewMyEstablishments);


        listViewMyEstablishments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Establishment item = adapter.getItem(position);

                Intent intent = new Intent(MyEstablishmentActivity.this, EstablishmentDetails.class);
                intent.putExtra("ESTABLISHMENT", item);

                startActivity(intent);
            }
        });


    }

   @Override
    protected void onStart() {
        super.onStart();
        HashMap<String, String> user = session.getUserDetails();
        String idUser = user.get(UserSessionController.KEY_LOGIN);

       try {
           adapter = new ListMyEstablishmentAdapter(this, establishmentController.listMyEstablishments(idUser));

       } catch (Exception e) {
           e.printStackTrace();
       }
       listViewMyEstablishments.setAdapter(adapter);

   }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_establishment, menu);
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
}
