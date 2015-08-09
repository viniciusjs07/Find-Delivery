package finddelivery.es.projeto.finddelivery.views;

import android.app.AlertDialog;
import android.content.Context;
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
import finddelivery.es.projeto.finddelivery.controllers.UserController;
import finddelivery.es.projeto.finddelivery.models.User;


public class LoginActivity extends ActionBarActivity {

    private EditText loginEditText;
    private EditText passwordEditText;
    private Button btnSingUp;
    private Context context;
    private UserController userController;
    private AlertDialog.Builder alert;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        userController = UserController.getInstance(context);
        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

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


        try {
            testaInicializacao();
        } catch (Exception e) {
            showDialog("Erro inicializando banco de dados");
            e.printStackTrace();
        }

    }


    public void testaInicializacao() throws Exception {
        if (userController.findAll().isEmpty()) {
           // User user = new User(0,"dani", "123456");
            User user = new User(0,"dani", "123456");//Name vazio
            userController.insert(user);
        }
    }

    /**
     *
     */
    public void showDialog(String mensagem) {
        alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", null);
        alert.setMessage(mensagem);
        alert.create().show();
    }

    public void validates(View view) {
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        try {
            boolean isValid = userController.validatesLogin(login, password);
            if (isValid) {
                Intent it = new Intent();
                it.setClass(LoginActivity.this,
                        EstablishmentsActivity.class);
                startActivity(it);
                finish();
            } else {
                showDialog("Login ou senha invalidos!");
                passwordEditText.setText("");

            }
        } catch (Exception e) {
            showDialog("Erro validando login e senha");
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
}