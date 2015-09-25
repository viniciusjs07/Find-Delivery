package finddelivery.es.projeto.finddelivery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.controllers.EstablishmentController;
import finddelivery.es.projeto.finddelivery.models.Establishment;

/**
 * Created by Vinicius on 29/07/2015.
 */
public class ListMyEstablishmentAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Establishment> items;
    private Context myContext;
    EstablishmentController establishmentController;

    public ListMyEstablishmentAdapter(Context context, List<Establishment> items) {
        this.items = items;
        mInflater = LayoutInflater.from(context);
        myContext = context;
        establishmentController = EstablishmentController.getInstance(context);
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
        view = mInflater.inflate(R.layout.activity_establishment_list, null);

        byte[] photo = (establishmentController.getEstablishment(item.getName())).getPhoto();
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);


        ((TextView) view.findViewById(R.id.textViewNameRestaurant)).setText(item.getName());
        ((TextView) view.findViewById(R.id.textViewSpeciallity)).setText(item.getSpeciality());
        ((ImageView) view.findViewById(R.id.imageViewRestaurant)).setImageBitmap(photoBitmap);
        ((ImageView) view.findViewById(R.id.imageViewRestaurant)).setImageBitmap(Bitmap.createScaledBitmap(photoBitmap, 100, 100, false));



        return view;
    }
}
