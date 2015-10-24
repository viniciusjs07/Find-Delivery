package finddelivery.es.projeto.finddelivery.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.DrawerListAdapter;
import finddelivery.es.projeto.finddelivery.models.NavItem;
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
    private UserSessionController session;
    private HashMap<String, String> user;
    private Map<User,String> mapComment = null;
    private Map<User,String> mapEvaluation = null;
    private User userLogged;
    private ActionBar actionBar;
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems;
    private ImageView photoUser2;
    private TextView nameUser;
    private TextView login2;

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
        establishmentPhotoImageView.setImageBitmap(Bitmap.createScaledBitmap(photoBitmap, 100, 100, false));

        actionBar =  getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.icon_menu);

        mNavItems = new ArrayList<NavItem>();
        mNavItems.add(new NavItem("Início", R.drawable.home));
        mNavItems.add(new NavItem("Meu perfil", R.drawable.profileuser));
        mNavItems.add(new NavItem("Meus restaurantes", R.drawable.myrestaurants));
        mNavItems.add(new NavItem("Novo restaurante", R.drawable.addrestaurant));
        mNavItems.add(new NavItem("Sair", R.drawable.logout));
        photoUser2 = (ImageView) findViewById((R.id.photoUser));
        nameUser = (TextView) findViewById(R.id.nameUser);
        login2 = (TextView) findViewById(R.id.login);

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
                    setView(EvaluateEstablishmentActivity.this, EstablishmentsActivity.class);
                }
                if (position == 1) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(EvaluateEstablishmentActivity.this, UserProfileActivity.class);
                }
                if (position == 2) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(EvaluateEstablishmentActivity.this, MyEstablishmentActivity.class);
                }
                if (position == 3) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(EvaluateEstablishmentActivity.this, EstablishmentCadastreActivity.class);
                }
                if (position == 4) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    session.logoutUser();
                    Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                    ComponentName cn = it.getComponent();
                    Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                    startActivity(mainIntent);
                }
            }
        });


        user = session.getUserDetails();
        String name = user.get(UserSessionController.KEY_NAME);
        String login = user.get(UserSessionController.KEY_LOGIN);
        String password = user.get(UserSessionController.KEY_PASSWORD);
        String photoUser = user.get(UserSessionController.KEY_PHOTO);
        byte[] photoUserByte = Base64.decode(photoUser, Base64.DEFAULT);

        userLogged = new User(name, login, password, photoUserByte);
        Bitmap photoUserBitmap = BitmapFactory.decodeByteArray(photoUserByte, 0, photoUserByte.length);

        photoUser2.setImageBitmap(photoUserBitmap);
        photoUser2.setImageBitmap(Bitmap.createScaledBitmap(photoUserBitmap, 50, 50, false));
        nameUser.setText(name);
        login2.setText(login);

        yourEvaluationForEstablishment.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Float grade = yourEvaluationForEstablishment.getRating();
                String idUser = user.get(UserSessionController.KEY_LOGIN);
                try{
                    if(!mapEvaluation.containsKey(userLogged)){
                        evaluationController.insertEvaluation(idUser, establishment.getName(), grade);
                        Toast.makeText(getApplicationContext(),
                                "Avaliação realizada com sucesso!",
                                Toast.LENGTH_LONG).show();


                        Map<User,String> evaluations = evaluationController.searchEvaluationByEstablishment(establishment.getName());
                        Float average = evaluationController.average(evaluations, establishment.getName());
                        averageTextView.setText(String.format("%.1f", average));
                        averageOfEstablishment.setRating(average);

                    } else {
                        evaluationController.updateEvaluation(idUser, establishment.getName(), grade);
                        Toast.makeText(getApplicationContext(),
                                "Avaliação atualizada com sucesso!",
                                Toast.LENGTH_LONG).show();

                        Map<User,String> evaluations = evaluationController.searchEvaluationByEstablishment(establishment.getName());
                        Float average = evaluationController.average(evaluations, establishment.getName());
                        averageTextView.setText(String.format("%.1f", average));
                        averageOfEstablishment.setRating(average);
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
            if(mapEvaluation != null && !mapEvaluation.isEmpty() ) {
                Float average = evaluationController.average(mapEvaluation, establishment.getName());
                averageTextView.setText(String.format("%.1f", average));
                averageOfEstablishment.setRating(average);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle(establishment.getName());
    }

    public void insertComment(View view) throws Exception {
        String comment = insertComment.getText().toString();

        if(comment != null && !comment.trim().equals("")){
            yourComment.setText(comment);
            insertComment.setText("");
            String idUser = user.get(UserSessionController.KEY_LOGIN);
            if(!mapComment.containsKey(userLogged)){
                commentController.insertComment(idUser, establishment.getName(), comment);
                Toast.makeText(getApplicationContext(),
                        "Comentário enviado com sucesso!",
                        Toast.LENGTH_LONG).show();
            } else{
                commentController.updateComment(idUser, establishment.getName(), comment);
                Toast.makeText(getApplicationContext(),
                        "Comentário atualizado com sucesso!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setView(Context context, Class classe){
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
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
        /*        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
