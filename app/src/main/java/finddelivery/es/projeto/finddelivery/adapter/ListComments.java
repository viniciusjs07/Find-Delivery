package finddelivery.es.projeto.finddelivery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
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
    EstablishmentController establishmentController;
    UserController userController;

    public ListComments(Context context, List<User> users, List<String> comments) {
       this.users = users;
        this.comments = comments;
        mInflater = LayoutInflater.from(context);
        myContext = context;
        establishmentController = EstablishmentController.getInstance(context);
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


        ((TextView) view.findViewById(R.id.textViewComment)).setText(itemComment);
        ((ImageView) view.findViewById(R.id.photoUser)).setImageBitmap(photoBitmap);
        ((ImageView) view.findViewById(R.id.photoUser)).setImageBitmap(Bitmap.createScaledBitmap(photoBitmap, 100, 100, false));

        return view;

    }
}
