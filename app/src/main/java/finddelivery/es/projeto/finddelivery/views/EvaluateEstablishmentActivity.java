package finddelivery.es.projeto.finddelivery.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.CommentController;
import finddelivery.es.projeto.finddelivery.controllers.EvaluationController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;
import finddelivery.es.projeto.finddelivery.models.Establishment;
import finddelivery.es.projeto.finddelivery.models.User;

public class EvaluateEstablishmentActivity extends ActionBarActivity {

    private RatingBar yourEvaluationForEstablishment;
    private RatingBar averageOfEstablishment;
    private TextView averageTextView;
    private TextView yourComment;
    private EditText insertComment;
    private CommentController commentController;
    private EvaluationController evaluationController;
    private Context context;
    private Establishment establishment;
    private TextView establishmentNameTextView;
    private TextView specialityTypeTextView;
    private ImageView establishmentPhotoImageView;

    UserSessionController session;
    private HashMap<String, String> user;
    private Map<User,String> mapComment = null;
    private Map<User,String> mapEvaluation = null;
    private User userLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_establishment);

        Intent it = getIntent();
        establishment = (Establishment) it.getSerializableExtra("ESTABLISHMENTEVALUATION");

        context = this;
        commentController = CommentController.getInstance(context);
        evaluationController = EvaluationController.getInstance(context);
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

        user = session.getUserDetails();
        String name = user.get(UserSessionController.KEY_NAME);
        String login = user.get(UserSessionController.KEY_LOGIN);
        String password = user.get(UserSessionController.KEY_PASSWORD);
        String photoUser = user.get(UserSessionController.KEY_PHOTO);
        byte[] photoUserByte = Base64.decode(photoUser, Base64.DEFAULT);

        userLogged = new User(name, login, password, photoUserByte);

        yourEvaluationForEstablishment.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Float grade = yourEvaluationForEstablishment.getRating();
                String idUser = user.get(UserSessionController.KEY_LOGIN);
                try{
                    if(!mapEvaluation.containsKey(userLogged)){
                        evaluationController.insert(idUser, establishment.getName(), grade);
                        Toast.makeText(getApplicationContext(),
                                "Avaliação realizada com sucesso!",
                                Toast.LENGTH_LONG).show();

                        Map<User,String> evaluations = evaluationController.searchEvaluationByEstablishment(establishment.getName());
                        averageTextView.setText(String.valueOf(average(evaluations)));
                        averageOfEstablishment.setRating(average(evaluations));
                    } else {
                        evaluationController.update(idUser, establishment.getName(), grade);
                        Toast.makeText(getApplicationContext(),
                                "Avaliação atualizada com sucesso!",
                                Toast.LENGTH_LONG).show();

                        Map<User,String> evaluations = evaluationController.searchEvaluationByEstablishment(establishment.getName());
                        averageTextView.setText(String.valueOf(average(evaluations)));
                        averageOfEstablishment.setRating(average(evaluations));
                    }

                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),
                            "Erro ao salvar avaliação!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });


        try {
            mapComment = commentController.searchCommentByEstablishment(establishment.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(mapComment != null && !mapComment.isEmpty()) {
            if (mapComment.containsKey(userLogged)) {
                String comment = mapComment.get(userLogged);
                yourComment.setText(comment);
            }
        }

        try {
            mapEvaluation = evaluationController.searchEvaluationByEstablishment(establishment.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(mapEvaluation != null && !mapEvaluation.isEmpty()) {

            averageTextView.setText(String.valueOf(average(mapEvaluation)));
            averageOfEstablishment.setRating(average(mapEvaluation));
        }
    }

    public Float average(Map<User,String> mapEvaluation){
        Collection<String> grades = mapEvaluation.values();
        Float sum = Float.valueOf(0);
        for (String grade: grades){
            sum += Float.valueOf(grade);
        }
        Float average = sum / grades.size();
        return average;
    }

    public void insertComment(View view) throws Exception {
        String comment = insertComment.getText().toString();

        if(comment != null && !comment.trim().equals("")){
            yourComment.setText(comment);
            insertComment.setText("");
            String idUser = user.get(UserSessionController.KEY_LOGIN);
            if(!mapComment.containsKey(userLogged)){
                commentController.insert(idUser, establishment.getName(), comment);
                Toast.makeText(getApplicationContext(),
                        "Comentário enviado com sucesso!",
                        Toast.LENGTH_LONG).show();
            } else{
                commentController.update(idUser, establishment.getName(), comment);
                Toast.makeText(getApplicationContext(),
                        "Comentário atualizado com sucesso!",
                        Toast.LENGTH_LONG).show();
            }
        }

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
