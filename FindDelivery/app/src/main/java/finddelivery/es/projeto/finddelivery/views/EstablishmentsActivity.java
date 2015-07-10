package finddelivery.es.projeto.finddelivery.views;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import finddelivery.es.projeto.finddelivery.R;

public class EstablishmentsActivity extends ActionBarActivity {

    private Button btnAdvancedSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishments);

        btnAdvancedSearch = (Button) findViewById(R.id.btnAdvancedSearch);

        btnAdvancedSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(EstablishmentsActivity.this,
                        FindEstablishmentActivity.class);
                startActivity(it);
                finish();
            }
        });

        TabHost abas = (TabHost) findViewById(R.id.tabhost);
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
