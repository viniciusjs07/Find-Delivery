package finddelivery.es.projeto.finddelivery.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.ListComments;
import finddelivery.es.projeto.finddelivery.adapter.ListMyEstablishmentAdapter;
import finddelivery.es.projeto.finddelivery.controllers.CommentController;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.models.Establishment;
import finddelivery.es.projeto.finddelivery.models.User;

public class EvaluationEstablishmentActivity extends ActionBarActivity {

    private Establishment establishment;
    private Button btnEvaluateEstablishment;
    private TextView establishmentName;
    private TextView specialityTypeTextView;
    private ImageView establishmentPhotoImageView;
    private ListView listView;
    private Map<User,String> mapComment;
    Context context;
    private LayoutInflater mInflater;
    EstablishmentController establishmentController;
    private ListComments adapter;
    CommentController commentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_establishment);

        mapComment = null;
        Intent it = getIntent();
        establishment = (Establishment) it.getSerializableExtra("ESTABLISHMENTDETAILS");
        mapComment = null;
        context = this;
        establishmentController = EstablishmentController.getInstance(context);
        commentController = CommentController.getInstance(context);

        establishmentName = (TextView)findViewById(R.id.establishmentName);
        establishmentName.setText(establishment.getName());
        specialityTypeTextView = (TextView)findViewById(R.id.specialityTypeTextView);
        specialityTypeTextView.setText(establishment.getSpeciality());
        establishmentPhotoImageView = (ImageView)findViewById(R.id.establishmentPhotoImageView);
        listView = (ListView) findViewById(R.id.listView);

        byte[] photo = establishment.getPhoto();
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        establishmentPhotoImageView.setImageBitmap(photoBitmap);

        btnEvaluateEstablishment = (Button) findViewById(R.id.btnEvaluateEstablishment);

        btnEvaluateEstablishment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(EvaluationEstablishmentActivity.this,
                        EvaluateEstablishmentActivity.class);
                it.putExtra("ESTABLISHMENTEVALUATION", establishment);

                startActivity(it);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            mapComment = commentController.searchCommentByEstablishment(establishment.getName());

           Set<User> users =  mapComment.keySet();
            Collection<String> comments =  mapComment.values();

            List listComments = new ArrayList(comments);
            List listUsers = new ArrayList(users);


            //List<String> comments = new ArrayList();
           // comments.add("Legal");
           // comments.add("O erro n eh aquui");
          // comments.add("Aeeeeeeeeee!");
           // comments.add("Era culpa de Rayssa");

            //adapter = new ListComments(this, users, comments);
            adapter = new ListComments(this,listUsers, listComments);
            Toast.makeText(getApplicationContext(),
                    "Passando sim",
                    Toast.LENGTH_LONG).show();
            listView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluation_establishment, menu);
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
