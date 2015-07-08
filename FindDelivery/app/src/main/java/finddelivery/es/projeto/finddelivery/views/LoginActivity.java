package finddelivery.es.projeto.finddelivery.views;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.*;
import android.content.Intent;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import finddelivery.es.projeto.finddelivery.R;

import java.util.ArrayList;

import finddelivery.es.projeto.finddelivery.Server.ConnectionHTTPClient;


public class LoginActivity extends ActionBarActivity {

    private EditText loginEditText;
    private EditText passwordEditText;
    private Button btnEnter;
    private Button btnSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        btnEnter = (Button) findViewById(R.id.btnEnter);
        btnSingUp = (Button) findViewById(R.id.btnSingUp);

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(LoginActivity.this,
                        UserCadastreActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String urlGet = "http://www.finddelivery.dx.am/login.ph?login=" + loginEditText.getText().toString() + "&senha=" + passwordEditText.getText().toString();
                String urlPost = "http://www.finddelivery.dx.am/login.php";
                ArrayList<NameValuePair> postParams = new ArrayList<>();
                postParams.add(new BasicNameValuePair("login", loginEditText.getText().toString()));
                postParams.add(new BasicNameValuePair("senha", passwordEditText.getText().toString()));
                String responseReturned = null;

                try {

                    responseReturned = ConnectionHTTPClient.executeHttpGet(urlGet);


                   // responseReturned = ConnectionHTTPClient.executeHttpPost(urlPost, postParams);
                    //messageShow("login", "passa");

                    String response = responseReturned.toString();
                    response = response.replaceAll("\\s+", "");

                    if (response.equals("1")) {
                        messageShow("login", "Login válido");
                    } else {
                        messageShow("login", "Login inválido");
                    }
                } catch (Exception error) {
                    messageShow("erro", "ERRO");

                }

            }

        });
    }

    public void messageShow(String titulo, String texto) {
        AlertDialog.Builder message = new AlertDialog.Builder(LoginActivity.this);
        message.setTitle(titulo);
        message.setMessage(texto);
        message.setNeutralButton("OK", null);
        message.show();

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