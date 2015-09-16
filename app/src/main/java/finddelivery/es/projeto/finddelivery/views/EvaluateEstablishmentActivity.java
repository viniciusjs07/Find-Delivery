package finddelivery.es.projeto.finddelivery.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.CommentController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;
import finddelivery.es.projeto.finddelivery.models.Establishment;

public class EvaluateEstablishmentActivity extends ActionBarActivity {

    private RatingBar yourEvaluationForEstablishment;
    private RatingBar averageOfEstablishment;
    private TextView averageTextView;
    private TextView yourComment;
    private EditText insertComment;
    private CommentController commentController;
    private Context context;
    private Establishment establishment;
    private TextView establishmentNameTextView;
    private TextView specialityTypeTextView;
    private ImageView establishmentPhotoImageView;

    UserSessionController session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_establishment);

        Intent it = getIntent();
        establishment = (Establishment) it.getSerializableExtra("ESTABLISHMENTEVALUATION");

        context = this;
        commentController = CommentController.getInstance(context);
        session = new  UserSessionController(getApplicationContext());

        yourEvaluationForEstablishment = (RatingBar)findViewById(R.id.yourEvaluationForEstablishment);
        averageOfEstablishment = (RatingBar)findViewById(R.id.averageOfEstablishment);
        averageTextView = (TextView)findViewById(R.id.averageTextView);
        insertComment = (EditText)findViewById(R.id.insertComment);
        yourComment = (TextView)findViewById(R.id.yourComment);
        establishmentNameTextView = (TextView)findViewById(R.id.establishmentNameTextView);
        establishmentNameTextView.setText(establishment.getName());
        specialityTypeTextView = (TextView)findViewById(R.id.specialityTypeTextView);
        specialityTypeTextView.setText(establishment.getSpeciality());
        establishmentPhotoImageView = (ImageView)findViewById(R.id.establishmentPhotoImageView);
        byte[] photo = establishment.getPhoto();
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        establishmentPhotoImageView.setImageBitmap(photoBitmap);


        yourEvaluationForEstablishment.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float grade = yourEvaluationForEstablishment.getRating();
                averageOfEstablishment.setRating(grade);
                String gradeText = String.valueOf(grade);
                //setar para a media do estabelecimento, por enquanto estamos testando com a nota
                averageTextView.setText(gradeText);
            }
        });
    }

    public void insertComment(View view) throws Exception {
        String comment = insertComment.getText().toString();
        if(comment != null && !comment.trim().equals("")){
            yourComment.setText(comment);
            insertComment.setText("");
        }

        HashMap<String, String> user = session.getUserDetails();
        String idUser = user.get(UserSessionController.KEY_LOGIN);
        commentController.insert(idUser, establishment.getName(), comment);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluate_establishment, menu);
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
