package finddelivery.es.projeto.finddelivery.views;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.ListMyEstablishmentAdapter;
import finddelivery.es.projeto.finddelivery.models.Establishment;

public class EstablishmentsActivity extends ActionBarActivity {

    private Button btnAdvancedSearch;
    private ListView listViewEstablishments;
    private ListMyEstablishmentAdapter adapter;
    private  List<Establishment> est;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishments);

        btnAdvancedSearch = (Button) findViewById(R.id.btnAdvancedSearch);
        listViewEstablishments = (ListView) findViewById(R.id.listViewEstablishments);
/*
       listViewEstablishments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view,
                                   int position, long id) {
               Toast.makeText(getApplicationContext(),
                       "Click ListItem Number " + position, Toast.LENGTH_LONG)
                       .show();
           }
       });*/

        listViewEstablishments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Establishment item = adapter.getItem(position);

                Intent intent = new Intent(EstablishmentsActivity.this, LoginActivity.class);
                intent.putExtra("ESTABLISHMENT", item);

                startActivity(intent);
            }
        });

        Intent i = getIntent();

       // est = EstablishmentController..getEstablishments();
        est =  new ArrayList<>();

        est.add(new Establishment(i.getStringExtra("name")));
        est.add(new Establishment("Nome 2" ));
        est.add(new Establishment("Nome 3" ));
        est.add(new Establishment("Nome 4"));


        btnAdvancedSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(EstablishmentsActivity.this,
                        FindEstablishmentActivity.class);
                startActivity(it);
                finish();
            }
        });

        TabHost abas = (TabHost) findViewById(R.id.tabHost);
        abas.setup();

        TabHost.TabSpec descritor = abas.newTabSpec("aba1");
        descritor.setContent(R.id.find_restaurantes);
        descritor.setIndicator(getString(R.string.title_tab_findRestaurants));
        abas.addTab(descritor);

        descritor = abas.newTabSpec("aba2");
        descritor.setContent(R.id.my_restaurantes);
        descritor.setIndicator(getString(R.string.title_tab_myRestaurantes));
        abas.addTab(descritor);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ListMyEstablishmentAdapter(this, est);
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