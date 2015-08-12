package finddelivery.es.projeto.finddelivery.views;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.app.AlertDialog;

import java.util.HashMap;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.UserController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;
import finddelivery.es.projeto.finddelivery.models.User;


public class ProfileEditActivity extends ActionBarActivity  {

    private Button btnCancelar;
    private Button btnSalvarAteracaoes;
    private ImageView imageViewUserProfile2;
    private EditText editTextNameUser2;
    private EditText editTextActualPassword;
    private EditText editTextNewPassword;
    private EditText editTextNewPasswordConfirm;
    private UserController userController;
    private Context context;
    private AlertDialog.Builder alert;
    private HashMap<String, String> user;

    UserSessionController session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        context = this;
        userController = UserController.getInstance(context);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnSalvarAteracaoes = (Button) findViewById(R.id.btnSalvarAlteracoes);
        editTextNameUser2 = (EditText) findViewById(R.id.editTextNameUser2);
        editTextActualPassword = (EditText) findViewById(R.id.editTextActualPassword);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        editTextNewPasswordConfirm = (EditText) findViewById(R.id.editTextNewPasswordConfirm);
        session = new  UserSessionController(getApplicationContext());
        user = session.getUserDetails();

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(), Toast.LENGTH_LONG).show();

        if(session.checkLogin()) {
            finish();
        }



        String name = user.get(UserSessionController.KEY_NAME);
        editTextNameUser2.setText(name);


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                session.logoutUser(); //Temp
                Intent it = new Intent();
                it.setClass(ProfileEditActivity.this,
                        LoginActivity.class);//Temp
                //  UserProfileActivity.class);
                startActivity(it);
                finish();
            }
        });
/*
        btnSalvarAteracaoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.dialog_changesSaved, Toast.LENGTH_SHORT).show();
                Intent it = new Intent();
                it.setClass(ProfileEditActivity.this, UserProfileActivity.class);
                startActivity(it);
                finish();
            }
        });*/
    }

    public void showDialog(String mensagem) {
        alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", null);
        alert.setMessage(mensagem);
        alert.create().show();
    }

    public void validates(View view) throws Exception {
        String name = editTextNameUser2.getText().toString();
        String actualPassword = editTextActualPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String newPasswordConfirm = editTextNewPasswordConfirm.getText().toString();

        String password = user.get(UserSessionController.KEY_PASSWORD);
        String login = user.get(UserSessionController.KEY_LOGIN);

        boolean passwordsValid = userController.validatesPasswords(newPassword, newPasswordConfirm);

        try {
            if(!password.equals(actualPassword)){
                showDialog("Senha atual invalida!");
                editTextActualPassword.setText("");
            }

            if (name != null || !name.equals("")){
                if (!passwordsValid) {
                    showDialog("Senha invalida!");
                    editTextActualPassword.setText("");
                    editTextNewPassword.setText("");
                    editTextNewPasswordConfirm.setText("");

                } else {
                    userController.updateData(name, login, newPassword);
                    showDialog("Dados alterados com sucesso!");
                    Intent it = new Intent();
                    it.setClass(ProfileEditActivity.this,
                            UserProfileActivity.class);
                    startActivity(it);
                    finish();
                }
            } else if (name == null || name.equals("")) {
                showDialog("Nome invalido!");
                editTextNameUser2.setText("");
                editTextActualPassword.setText("");
                editTextNewPassword.setText("");
                editTextNewPasswordConfirm.setText("");
            }
        }
        catch (Exception e){
            showDialog("Erro validando usuario");
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil_edit, menu);
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

    public void showUserProfile() {
        setContentView(R.layout.activity_user_profile);
    }
}
