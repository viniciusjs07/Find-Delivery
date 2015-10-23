package finddelivery.es.projeto.finddelivery.views;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.IntentCompat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.DrawerListAdapter;
import finddelivery.es.projeto.finddelivery.models.NavItem;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;

public class FindEstablishmentActivity extends ActionBarActivity {

    private Spinner sp;
    private List<String> specialityTypes;
    private EditText restaurantNameEditText;
    private Button searchByName;
    private Button searchBySpeciality;
    private EstablishmentController establishmentController;
    private Context context;
    private AlertDialog.Builder alert;
    private ActionBar actionBar;
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems;
    private UserSessionController session;
    private ImageView photoUser;
    private TextView nameUser;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_establishment);
        session = new  UserSessionController(getApplicationContext());

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
                    setView(FindEstablishmentActivity.this, EstablishmentsActivity.class);
                }
                if (position == 1){
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(FindEstablishmentActivity.this, UserProfileActivity.class);
                }
                if (position == 2){
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(FindEstablishmentActivity.this, MyEstablishmentActivity.class);
                }
                if (position == 3){
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(FindEstablishmentActivity.this, EstablishmentCadastreActivity.class);
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
        context = this;
        establishmentController = EstablishmentController.getInstance(context);

        specialityTypes = new ArrayList<String>();
        addTypes();

        restaurantNameEditText = (EditText)findViewById(R.id.restaurantNameEditText);
        searchByName = (Button)findViewById(R.id.searchByName);
        searchBySpeciality = (Button)findViewById(R.id.searchBySpeciality);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, specialityTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp = (Spinner) findViewById(R.id.spinnerTipoCozinha);
        sp.setAdapter(adapter);
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

    public void searchByName(View view) throws Exception{
        try {
            String name = restaurantNameEditText.getText().toString();
            establishmentController.listByName(name);
            establishmentController.isSearchAdvanced = true;
            establishmentController.isSearchAdvancedByName = true;
            Intent it = new Intent();
            it.setClass(FindEstablishmentActivity.this,
                    EstablishmentsActivity.class);
            startActivity(it);
            restaurantNameEditText.setText("");
        }catch (Exception e) {
            showDialog("Erro de busca!");
            e.printStackTrace();
        }
    }

    public void searchBySpeciality(View view) throws Exception{
      try {
          String speciality = sp.getSelectedItem().toString();
          establishmentController.listBySpeciality(speciality);
          establishmentController.isSearchAdvanced = true;
          establishmentController.isSearchAdvancedByName = false;
          Intent it = new Intent();
          it.setClass(FindEstablishmentActivity.this,
                  EstablishmentsActivity.class);
          startActivity(it);

      }catch (Exception e) {
          showDialog("Erro de busca!");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_establishment, menu);
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
