package finddelivery.es.projeto.finddelivery.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.DrawerListAdapter;
import finddelivery.es.projeto.finddelivery.adapter.NavItem;
import finddelivery.es.projeto.finddelivery.controllers.EvaluationController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;
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
    private ActionBar actionBar;


    private ImageView photoUser;
    private TextView nameUser;
    private TextView login;
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    UserSessionController session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_details);

        actionBar =  getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        session = new  UserSessionController(getApplicationContext());

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

        mNavItems.add(new NavItem("Início", R.drawable.home));
        mNavItems.add(new NavItem("Meu perfil", R.drawable.profileuser));
        mNavItems.add(new NavItem("Meus restaurantes", R.drawable.myrestaurants));
        mNavItems.add(new NavItem("Novo restaurante", R.drawable.addrestaurant));
        mNavItems.add(new NavItem("Sair", R.drawable.logout));
        photoUser = (ImageView) findViewById((R.id.photoUser));
        nameUser = (TextView) findViewById(R.id.nameUser);
        login = (TextView) findViewById(R.id.login);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter drawerAdapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(drawerAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(EstablishmentDetails.this,
                            EstablishmentsActivity.class);
                    startActivity(it);
                }
                if (position == 1) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(EstablishmentDetails.this,
                            UserProfileActivity.class);
                    startActivity(it);
                }
                if (position == 2) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(EstablishmentDetails.this,
                            MyEstablishmentActivity.class);
                    startActivity(it);
                }
                if (position == 3) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(EstablishmentDetails.this,
                            EstablishmentCadastreActivity.class);
                    startActivity(it);
                }
                if (position == 4) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    session.logoutUser();
                    Intent it = new Intent();
                    it.setClass(EstablishmentDetails.this,
                            LoginActivity.class);
                    startActivity(it);
                }
            }
        });


        Map<String, String> user = session.getUserDetails();

        String photoU = user.get(UserSessionController.KEY_PHOTO);
        String name = user.get(UserSessionController.KEY_NAME);
        String loginUser = user.get(UserSessionController.KEY_LOGIN);


        byte[] photoUserByte = Base64.decode(photoU, Base64.DEFAULT);

        Bitmap photoUserBitmap = BitmapFactory.decodeByteArray(photoUserByte, 0, photoUserByte.length);

        photoUser.setImageBitmap(photoUserBitmap);
        photoUser.setImageBitmap(Bitmap.createScaledBitmap(photoUserBitmap, 50, 50, false));
        nameUser.setText(name);
        login.setText(loginUser);

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