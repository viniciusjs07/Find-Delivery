package finddelivery.es.projeto.finddelivery;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.*;
import android.view.MenuItem;
import android.widget.Button;


public class UserProfileActivity extends ActionBarActivity {

    Button btnAlterarDados;
    Button btnExcluirConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        btnAlterarDados = (Button) findViewById(R.id.btnAlterarDados);
        btnExcluirConta = (Button) findViewById(R.id.btnExcluirConta);

        btnAlterarDados.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProfileEdite();
            }
        });


        btnExcluirConta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showLogin();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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

    public void showProfileEdite() {
        setContentView(R.layout.activity_perfil_edit);
    }

    public void showUserProfile() {
        setContentView(R.layout.activity_user_profile);
    }

    public void showLogin() {
        setContentView(R.layout.activity_login);
    }
}
