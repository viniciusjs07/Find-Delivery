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
import finddelivery.es.projeto.finddelivery.models.Establishment;
import finddelivery.es.projeto.finddelivery.models.User;

public class EstablishmentsListAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Establishment> items;
    private EvaluationController evaluationController;
    private Map<User,String> mapEvaluation = null;
    EstablishmentController establishmentController;

    public EstablishmentsListAdapter(Context context, List<Establishment> items) {
        this.items = items;
        mInflater = LayoutInflater.from(context);
        establishmentController = EstablishmentController.getInstance(context);
        evaluationController = EvaluationController.getInstance(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Establishment getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Establishment item = items.get(position);
        view = mInflater.inflate(R.layout.establishment_item, null);

        byte[] photo = (establishmentController.getEstablishment(item.getName())).getPhoto();
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);

       try {
            mapEvaluation = evaluationController.searchEvaluationByEstablishment(item.getName());
            if(mapEvaluation != null && !mapEvaluation.isEmpty()) {
                Float average = evaluationController.average(mapEvaluation, item.getName());

                ((RatingBar) view.findViewById(R.id.myRatingBar)).setRating(average);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((TextView) view.findViewById(R.id.textViewNameRestaurant)).setText(item.getName());
        ((TextView) view.findViewById(R.id.textViewSpeciallity)).setText(item.getSpeciality());
        ((ImageView) view.findViewById(R.id.imageViewRestaurant)).setImageBitmap(photoBitmap);
        ((ImageView) view.findViewById(R.id.imageViewRestaurant)).setImageBitmap(Bitmap.createScaledBitmap(photoBitmap, 100, 100, false));

        return view;
    }
}
