package finddelivery.es.projeto.finddelivery.views;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.models.SpecialityType;


public class FindEstablishmentActivity extends ActionBarActivity {

    private Spinner sp;
    private List<String> specialityTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_find_establishment);

        specialityTypes = new ArrayList<String>();
        addTypes();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, specialityTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp = (Spinner) findViewById(R.id.spinnerTipoCozinha);
        sp.setAdapter(adapter);


    }

    private void addTypes() {
        specialityTypes.add("Comida Brasileira");
        specialityTypes.add("Comida Mexicana");
        specialityTypes.add("Comida Japonesa");
        specialityTypes.add("Comida Chinesa");
        specialityTypes.add("Comida Italiana");
        specialityTypes.add("Comida Variada");
        specialityTypes.add("Comida Saudavel");
        specialityTypes.add("Lanches");
        specialityTypes.add("Pizza");
        specialityTypes.add("Doces");
        specialityTypes.add("Salgados");
        specialityTypes.add("Frutos do Mar");
        specialityTypes.add("Cafe");
        specialityTypes.add("Carnes");
        specialityTypes.add("Bebidas");
        specialityTypes.add("Saladas");
        specialityTypes.add("Marmitas");
        specialityTypes.add("Massas");
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
