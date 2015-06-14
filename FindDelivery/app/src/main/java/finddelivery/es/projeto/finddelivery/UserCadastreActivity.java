package finddelivery.es.projeto.finddelivery;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.*;
import android.content.Intent;

public class UserCadastreActivity extends ActionBarActivity {

    private ImageView cadastrePhotoImageView;
    private EditText cadastreNameEditText;
    private EditText cadastreLoginEditText;
    private EditText cadastrePasswordEditText;
    private EditText cadastrePasswordConfirmEditText;
    private Button btnRegister;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cadastre);

        cadastrePhotoImageView = (ImageView)findViewById(R.id.cadastrePhotoImageView);
        cadastreNameEditText = (EditText)findViewById(R.id.cadastreNameEditText);
        cadastreLoginEditText = (EditText)findViewById(R.id.cadastreLoginEditText);
        cadastrePasswordEditText = (EditText)findViewById(R.id.cadastrePasswordEditText);
        cadastrePasswordConfirmEditText = (EditText)findViewById(R.id.cadastrePasswordConfirmEditText);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(UserCadastreActivity.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.dialog_registerOK, Toast.LENGTH_SHORT).show();
                Intent it = new Intent();
                it.setClass(UserCadastreActivity.this,
                        LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro_usuario, menu);
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
