package finddelivery.es.projeto.finddelivery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.controllers.EvaluationController;
import finddelivery.es.projeto.finddelivery.controllers.UserController;
import finddelivery.es.projeto.finddelivery.models.Establishment;
import finddelivery.es.projeto.finddelivery.models.User;

/**
 * Created by Admin on 23/09/2015.
 */


public class ListComments extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<User> users;
    private Context myContext;
    List<String> comments;
    private Establishment establishment;
    private EvaluationController evaluationController;
    private Map<User,String> mapEvaluation = null;
    EstablishmentController establishmentController;
    UserController userController;

    public ListComments(Context context, List<User> users, List<String> comments, Establishment establishment) {
        this.users = users;
        this.comments = comments;
        this.establishment = establishment;
        mInflater = LayoutInflater.from(context);
        myContext = context;
        establishmentController = EstablishmentController.getInstance(context);
        evaluationController = EvaluationController.getInstance(context);
        userController = UserController.getInstance(context);
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public String getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        User itemUser = users.get(position);
        String itemComment = comments.get(position);

        view = mInflater.inflate(R.layout.activity_comments_list, null);

        byte[] photoUser = (userController.getUser(itemUser.getLogin())).getPhoto();
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photoUser, 0, photoUser.length);

        try {
            mapEvaluation = evaluationController.searchEvaluationByEstablishment(establishment.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(mapEvaluation != null && !mapEvaluation.isEmpty()) {
            ((RatingBar) view.findViewById(R.id.myRatingBar)).setRating(evaluationController.average(mapEvaluation));
        }

        ((TextView) view.findViewById(R.id.textViewComment)).setText(itemComment);
        ((TextView) view.findViewById(R.id.textViewName)).setText(itemUser.getName());
        ((ImageView) view.findViewById(R.id.photoUser)).setImageBitmap(photoBitmap);
        ((ImageView) view.findViewById(R.id.photoUser)).setImageBitmap(Bitmap.createScaledBitmap(photoBitmap, 100, 100, false));

        return view;

    }
}
