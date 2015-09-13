package finddelivery.es.projeto.finddelivery.views;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import finddelivery.es.projeto.finddelivery.R;

public class EstablishmentDetails extends ActionBarActivity implements View.OnClickListener {

    private Button btnAvaliacoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_details);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_establishment_details, menu);

        final ImageButton btLigar = (ImageButton) findViewById(R.id.phone);
        btLigar.setOnClickListener(this);

        final ImageButton btLigar2 = (ImageButton) findViewById(R.id.phoneTwo);
        btLigar2.setOnClickListener(this);

        btnAvaliacoes = (Button) findViewById(R.id.btnAvaliacoes);

        btnAvaliacoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(EstablishmentDetails.this,
                        EvaluationEstablishmentActivity.class);
                startActivity(it);
                finish();
            }
        });
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

    @Override
    public void onClick(View v) {
        String telefone = null;

        switch (v.getId()){
            case R.id.phone:
                EditText campoTelefone = (EditText) findViewById(R.id.fieldPhone);
                telefone = campoTelefone.getText().toString();
                break;
            case R.id.phoneTwo:
                EditText campoTelefone2 = (EditText) findViewById(R.id.fieldPhoneTwo);
                 telefone = campoTelefone2.getText().toString();
                break;
        }
        Uri uri = Uri.parse("tel:"+telefone);
        Intent intent = new Intent(Intent.ACTION_DIAL,uri);
        startActivity(intent);

    }
}