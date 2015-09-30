package finddelivery.es.projeto.finddelivery.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;

public class FindEstablishmentActivity extends ActionBarActivity {

    private Spinner sp;
    private List<String> specialityTypes;
    private EditText restaurantNameEditText;
    private ImageButton searchByName;
    private ImageButton searchBySpeciality;
    private EstablishmentController establishmentController;
    private Context context;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_establishment);

        context = this;
        establishmentController = EstablishmentController.getInstance(context);

        specialityTypes = new ArrayList<String>();
        addTypes();

        restaurantNameEditText = (EditText)findViewById(R.id.restaurantNameEditText);
        searchByName = (ImageButton)findViewById(R.id.searchByName);
        searchBySpeciality = (ImageButton)findViewById(R.id.searchBySpeciality);

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
            establishmentController.insertByName(name);
            establishmentController.isSearchAdvanced = true;
            establishmentController.isIsSearchAdvancedByName = true;
            Intent it = new Intent();
            it.setClass(FindEstablishmentActivity.this,
                    EstablishmentsActivity.class);
            startActivity(it);
            finish();
        }catch (Exception e) {
            showDialog("Erro de busca!");
            e.printStackTrace();
        }
    }

    public void searchBySpeciality(View view) throws Exception{
      try {
          String speciality = sp.getSelectedItem().toString();
          establishmentController.insertBySpeciality(speciality);
          establishmentController.isSearchAdvanced = true;
          establishmentController.isIsSearchAdvancedByName = false;
          Intent it = new Intent();
          it.setClass(FindEstablishmentActivity.this,
                  EstablishmentsActivity.class);
          startActivity(it);
          finish();
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
