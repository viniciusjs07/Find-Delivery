package finddelivery.es.projeto.finddelivery.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Map;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.EvaluationController;
import finddelivery.es.projeto.finddelivery.models.Establishment;
import finddelivery.es.projeto.finddelivery.models.User;

public class EstablishmentDetails extends ActionBarActivity implements View.OnClickListener {

    private Button btnAvaliacoes;
    private TextView establishmentNameTextView;
    private TextView specialityTypeTextView;
    private ImageView establishmentPhotoImageView;
    private TextView averageOfEstablishmentTextView;
    private RatingBar evaluationEstablishmentRatingBar;
    private TextView businessHours;
    private TextView fieldPhone;
    private TextView fieldPhoneTwo;
    private Establishment establishment;
    private Context context;
    private EvaluationController evaluationController;
    private Map<User,String> mapEvaluation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_details);

        context = this;
        evaluationController = EvaluationController.getInstance(context);

        establishmentNameTextView = (TextView) findViewById(R.id.establishmentNameTextView);
        specialityTypeTextView = (TextView) findViewById(R.id.specialityTypeTextView);
        establishmentPhotoImageView = (ImageView) findViewById(R.id.establishmentPhotoImageView);
        averageOfEstablishmentTextView = (TextView) findViewById(R.id.averageOfEstablishmentTextView);
        evaluationEstablishmentRatingBar = (RatingBar) findViewById(R.id.evaluationEstablishmentRatingBar);
        businessHours = (TextView) findViewById(R.id.businessHours);
        fieldPhone = (TextView) findViewById(R.id.fieldPhone);
        fieldPhoneTwo = (TextView) findViewById(R.id.fieldPhoneTwo);

        final ImageButton btLigar = (ImageButton) findViewById(R.id.phone);
        btLigar.setOnClickListener(this);

        final ImageButton btLigar2 = (ImageButton) findViewById(R.id.phoneTwo);
        btLigar2.setOnClickListener(this);

        btnAvaliacoes = (Button) findViewById(R.id.btnAvaliacoes);

        Intent it = getIntent();
        establishment = (Establishment) it.getSerializableExtra("ESTABLISHMENT");

        byte[] photo = establishment.getPhoto();
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        establishmentNameTextView.setText(establishment.getName());
        specialityTypeTextView.setText(establishment.getSpeciality());
        establishmentPhotoImageView.setImageBitmap(photoBitmap);
        establishmentPhotoImageView.setImageBitmap(Bitmap.createScaledBitmap(photoBitmap, 100, 100, false));

        businessHours.setText(establishment.getBusinessHour());
        fieldPhone.setText(establishment.getPhone1());
        fieldPhoneTwo.setText(establishment.getPhone2());

        try {
            mapEvaluation = evaluationController.searchEvaluationByEstablishment(establishment.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(mapEvaluation != null && !mapEvaluation.isEmpty()) {
            averageOfEstablishmentTextView.setText(String.format("%.1f", evaluationController.average(mapEvaluation)));
            evaluationEstablishmentRatingBar.setRating(evaluationController.average(mapEvaluation));
        }

       setTitle(establishment.getName());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_establishment_details, menu);

        final ImageButton btLigar = (ImageButton) findViewById(R.id.phone);
        btLigar.setOnClickListener(this);

        final ImageButton btLigar2 = (ImageButton) findViewById(R.id.phoneTwo);
        btLigar2.setOnClickListener(this);

        btnAvaliacoes = (Button) findViewById(R.id.btnAvaliacoes);

        btnAvaliacoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(EstablishmentDetails.this,
                        EvaluationEstablishmentActivity.class);
                it.putExtra("ESTABLISHMENTDETAILS", establishment);

                startActivity(it);
            }
        });
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



    @Override
    public void onClick(View v) {
        String telefone = null;

        switch (v.getId()){
            case R.id.phone:
                EditText campoTelefone = (EditText) findViewById(R.id.fieldPhone);
                telefone = campoTelefone.getText().toString();
                break;
            case R.id.phoneTwo:
                EditText campoTelefone2 = (EditText) findViewById(R.id.fieldPhoneTwo);
                telefone = campoTelefone2.getText().toString();
                break;
        }
        Uri uri = Uri.parse("tel:"+telefone);
        Intent intent = new Intent(Intent.ACTION_DIAL,uri);
        startActivity(intent);

    }
}