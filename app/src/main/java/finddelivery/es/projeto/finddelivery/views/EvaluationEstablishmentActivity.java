package finddelivery.es.projeto.finddelivery.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.ListComments;
import finddelivery.es.projeto.finddelivery.controllers.CommentController;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.controllers.EvaluationController;
import finddelivery.es.projeto.finddelivery.models.Establishment;
import finddelivery.es.projeto.finddelivery.models.User;

public class EvaluationEstablishmentActivity extends ActionBarActivity {

    private Establishment establishment;
    private Button btnEvaluateEstablishment;
    private TextView establishmentName;
    private TextView specialityTypeTextView;
    private TextView averageOfEstablishmentTextView2;
    private ImageView establishmentPhotoImageView;
    private ListView listView;
    private RatingBar evaluationEstablishmentRatingBar2;
    private Map<User,String> mapComment = null;
    private Context context;
    private EstablishmentController establishmentController;
    private ListComments adapter;
    private CommentController commentController;
    private EvaluationController evaluationController;
    private Map<User,String> mapEvaluation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_establishment);

        Intent it = getIntent();
        establishment = (Establishment) it.getSerializableExtra("ESTABLISHMENTDETAILS");
        context = this;
        establishmentController = EstablishmentController.getInstance(context);
        commentController = CommentController.getInstance(context);
        evaluationController = EvaluationController.getInstance(context);

        establishmentName = (TextView)findViewById(R.id.establishmentName);
        establishmentName.setText(establishment.getName());
        specialityTypeTextView = (TextView)findViewById(R.id.specialityTypeTextView);
        specialityTypeTextView.setText(establishment.getSpeciality());
        establishmentPhotoImageView = (ImageView)findViewById(R.id.establishmentPhotoImageView);
        listView = (ListView) findViewById(R.id.listView);
        averageOfEstablishmentTextView2 = (TextView) findViewById(R.id.averageOfEstablishmentTextView2);
        evaluationEstablishmentRatingBar2 = (RatingBar) findViewById(R.id.evaluationEstablishmentRatingBar2);

        byte[] photo = establishment.getPhoto();
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        establishmentPhotoImageView.setImageBitmap(photoBitmap);
        establishmentPhotoImageView.setImageBitmap(Bitmap.createScaledBitmap(photoBitmap, 100, 100, false));


        btnEvaluateEstablishment = (Button) findViewById(R.id.btnEvaluateEstablishment);

        btnEvaluateEstablishment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(EvaluationEstablishmentActivity.this,
                        EvaluateEstablishmentActivity.class);
                it.putExtra("ESTABLISHMENTEVALUATION", establishment);

                startActivity(it);
            }
        });

        try {
            mapEvaluation = evaluationController.searchEvaluationByEstablishment(establishment.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(mapEvaluation != null && !mapEvaluation.isEmpty()) {
            averageOfEstablishmentTextView2.setText(String.format("%.1f", evaluationController.average(mapEvaluation)));
            evaluationEstablishmentRatingBar2.setRating(evaluationController.average(mapEvaluation));
        }


        setTitle(establishment.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

            adapter = new ListComments(this,listUsers, listComments);
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
