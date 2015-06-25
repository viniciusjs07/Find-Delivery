package finddelivery.es.projeto.finddelivery.views;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.*;
import android.content.Intent;
import finddelivery.es.projeto.finddelivery.R;


public class LoginActivity extends ActionBarActivity {

    private EditText loginEditText;
    private EditText passwordEditText;
    private Button btnEnter;
    private Button btnSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditText = (EditText)findViewById(R.id.loginEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        btnEnter = (Button)findViewById(R.id.btnEnter);
        btnSingUp = (Button)findViewById(R.id.btnSingUp);

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(LoginActivity.this,
                        UserCadastreActivity.class);
                startActivity(it);
                finish();
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
