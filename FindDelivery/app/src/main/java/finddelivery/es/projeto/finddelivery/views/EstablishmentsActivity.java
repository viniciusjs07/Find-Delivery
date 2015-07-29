package finddelivery.es.projeto.finddelivery.views;

import android.content.Intent;
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

import finddelivery.es.projeto.finddelivery.R;

public class EstablishmentsActivity extends ActionBarActivity {

    private Button btnAdvancedSearch;
    private ListView listViewEstablishments;

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

        // Defined Array values to show in ListView
        String[] values = new String[] { "Por enquanto",
                "Soh para mostrar",
                "Como vai ficar a lista",
                "Mas precisamos colocar",
                "Foto, nome, especialidade e",
                "Avaliacao do estabelecimento"

        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listViewEstablishments.setAdapter(adapter);

        // ListView Item Click Listener
        listViewEstablishments.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listViewEstablishments.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

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