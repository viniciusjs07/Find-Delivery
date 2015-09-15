package finddelivery.es.projeto.finddelivery.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.ListMyEstablishmentAdapter;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;

import finddelivery.es.projeto.finddelivery.models.Establishment;


import android.widget.AdapterView.OnItemClickListener;


public class EstablishmentsActivity extends ActionBarActivity {

    private Button btnAdvancedSearch;
    private ListView listViewEstablishments;
    private ListMyEstablishmentAdapter adapter;
    private Context context;
    EstablishmentController establishmentController;
    String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    ArrayAdapter<String> adapterArray;
    private AlertDialog.Builder alert;

    UserSessionController session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishments);

        context = this;
        establishmentController = EstablishmentController.getInstance(context);
        session = new  UserSessionController(getApplicationContext());

        btnAdvancedSearch = (Button) findViewById(R.id.btnAdvancedSearch);
        listViewEstablishments = (ListView) findViewById(R.id.listViewEstablishments);

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

        menu = new String[]{"Minha conta","Meus restaurantes", "Novo restaurante","Sair"};

        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dList = (ListView) findViewById(R.id.left_drawer);

        adapterArray = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu);

        dList.setAdapter(adapterArray);
        dList.setSelector(android.R.color.holo_blue_dark);
        dList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

                dLayout.closeDrawers();
                Bundle args = new Bundle();
                args.putString("Menu", menu[position]);

                if (position == 0 ){
                    Intent it = new Intent();
                    it.setClass(EstablishmentsActivity.this,
                            UserProfileActivity.class);
                    startActivity(it);
                    finish();
                }
                if (position == 1){
                    Intent it = new Intent();
                    it.setClass(EstablishmentsActivity.this,
                            MyEstablishmentActivity.class);
                    startActivity(it);
                    finish();
                }
                if (position == 2){
                    Intent it = new Intent();
                    it.setClass(EstablishmentsActivity.this,
                            EstablishmentCadastreActivity.class);
                    startActivity(it);
                    finish();
                }
                if (position == 3){
                    session.logoutUser();
                    Intent it = new Intent();
                    it.setClass(EstablishmentsActivity.this,
                            LoginActivity.class);
                    startActivity(it);
                    finish();
                }
            }

        });


        listViewEstablishments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Establishment item = adapter.getItem(position);

                Intent intent = new Intent(EstablishmentsActivity.this, LoginActivity.class);
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
                finish();
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
                        showDialog("Restaurante nao encontrado!");
                        Intent it = new Intent();
                        it.setClass(EstablishmentsActivity.this,
                                FindEstablishmentActivity.class);
                        startActivity(it);
                        finish();
                    }else {
                        adapter = new ListMyEstablishmentAdapter(this, establishmentsList);
                    }
                }else{
                    List<Establishment> establishmentsList = establishmentController.listBySpeciality();
                    if(establishmentsList.size() == 0){
                        showDialog("Restaurante nao encontrado!");
                        Intent it = new Intent();
                        it.setClass(EstablishmentsActivity.this,
                                FindEstablishmentActivity.class);
                        startActivity(it);
                        finish();
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