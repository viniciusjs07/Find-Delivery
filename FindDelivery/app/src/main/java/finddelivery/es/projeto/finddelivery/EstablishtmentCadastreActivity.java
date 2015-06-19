package finddelivery.es.projeto.finddelivery;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


public class EstablishtmentCadastreActivity extends ActionBarActivity {

    private ImageView imageViewEstablishmentLogo;
    private EditText establishmentName;
    private EditText establishmentAddress;
    private EditText establishmentEspeciality; // Transformar numa 'caixa select'
    private EditText establishmentCity;
    private EditText establishmentState;
    private EditText establishmentPhoneNumber;
    private EditText establishmentBusinessHour;
    private Button btnCadastrar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishtment_cadastre);

        imageViewEstablishmentLogo = (ImageView) findViewById(R.id.imageViewEstablishmentLogo);
        establishmentName = (EditText) findViewById(R.id.editTextEstablishmentName);
        establishmentEspeciality = (EditText) findViewById(R.id.editTextEspeciality);
        establishmentAddress = (EditText) findViewById(R.id.editTextAddress);
        establishmentPhoneNumber = (EditText) findViewById(R.id.editTextPhone);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrarEstabelecimento);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_establishtment_cadastre, menu);
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
