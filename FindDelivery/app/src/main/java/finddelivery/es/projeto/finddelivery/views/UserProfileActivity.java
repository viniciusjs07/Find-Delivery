package finddelivery.es.projeto.finddelivery.views;

import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.*;
import android.widget.*;
import android.app.AlertDialog;

import java.util.HashMap;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;


public class UserProfileActivity extends ActionBarActivity  {

    private AlertDialog alertDeleteAccount;
    private Button btnAlterarDados;
    private Button btnExcluirConta;
    private ImageView imageViewUserProfile;
    private TextView editTextNameUser;
    private TextView editTextLoginUser;

    UserSessionController session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        imageViewUserProfile = (ImageView) findViewById((R.id.imageViewUserProfile));
        editTextNameUser = (TextView) findViewById(R.id.editTextNameUser);
        editTextLoginUser = (TextView) findViewById(R.id.editTextLoginUser);
        btnAlterarDados = (Button) findViewById(R.id.btnAlterarDados);
        btnExcluirConta = (Button) findViewById(R.id.btnExcluirConta);

        session = new  UserSessionController(getApplicationContext());

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

        if(session.checkLogin()) {
            finish();
        }


        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(UserSessionController.KEY_NAME);
        String login = user.get(UserSessionController.KEY_LOGIN);

        String photoUser = user.get(UserSessionController.KEY_PHOTO);
        byte[] photoUserByte = Base64.decode(photoUser, Base64.DEFAULT);

        Bitmap photoUserBitmap = BitmapFactory.decodeByteArray(photoUserByte,0, photoUserByte.length);

       imageViewUserProfile.setImageBitmap(photoUserBitmap);

        editTextNameUser.setText(name);
        editTextLoginUser.setText(login);



        btnAlterarDados.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent it = new Intent();
                it.setClass(UserProfileActivity.this,
                        ProfileEditActivity.class);
                startActivity(it);
                finish();
            }
        });


        btnExcluirConta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder deleteAccount = new AlertDialog.Builder(UserProfileActivity.this);
                deleteAccount.setMessage(R.string.dialog_deleteAccount)
                        .setPositiveButton(R.string.dialog_positiveAwswer, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(), R.string.dialog_accountDeleted, Toast.LENGTH_SHORT).show();
                                Intent it = new Intent();
                                it.setClass(UserProfileActivity.this, LoginActivity.class);
                                startActivity(it);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.dialog_negativeAwswer, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                alertDeleteAccount = deleteAccount.create();
                alertDeleteAccount.show();

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

}
