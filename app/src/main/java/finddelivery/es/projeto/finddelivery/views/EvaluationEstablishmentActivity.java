package finddelivery.es.projeto.finddelivery.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.adapter.DrawerListAdapter;
import finddelivery.es.projeto.finddelivery.adapter.ListComments;
import finddelivery.es.projeto.finddelivery.adapter.NavItem;
import finddelivery.es.projeto.finddelivery.controllers.CommentController;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.controllers.EvaluationController;
import finddelivery.es.projeto.finddelivery.controllers.UserSessionController;
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
    private ActionBar actionBar;

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    UserSessionController session;
    private ImageView photoUser;
    private TextView nameUser;
    private TextView login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_establishment);

        actionBar =  getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        session = new  UserSessionController(getApplicationContext());

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

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(EvaluationEstablishmentActivity.this,
                            EstablishmentsActivity.class);
                    startActivity(it);
                }
                if (position == 1) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(EvaluationEstablishmentActivity.this,
                            UserProfileActivity.class);
                    startActivity(it);
                }
                if (position == 2) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(EvaluationEstablishmentActivity.this,
                            MyEstablishmentActivity.class);
                    startActivity(it);
                }
                if (position == 3) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    Intent it = new Intent();
                    it.setClass(EvaluationEstablishmentActivity.this,
                            EstablishmentCadastreActivity.class);
                    startActivity(it);
                }
                if (position == 4) {
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    session.logoutUser();
                    Intent it = new Intent();
                    it.setClass(EvaluationEstablishmentActivity.this,
                            LoginActivity.class);
                    startActivity(it);
                }
            }
        });



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

            adapter = new ListComments(this,listUsers, listComments, establishment);
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
