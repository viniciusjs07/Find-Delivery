package finddelivery.es.projeto.finddelivery;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.app.Dialog;
import android.app.AlertDialog;

public class ProfileEditActivity extends ActionBarActivity  {

    private Button btnCancelar;
    private Button btnSalvarAteracaoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_edit);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnSalvarAteracaoes = (Button) findViewById(R.id.btnSalvarAlteracoes);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(ProfileEditActivity.this,
                        UserProfileActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnSalvarAteracaoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.dialog_changesSaved, Toast.LENGTH_SHORT).show();
                Intent it = new Intent();
                it.setClass(ProfileEditActivity.this, UserProfileActivity.class);
                startActivity(it);
                finish();
            }
        });
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
