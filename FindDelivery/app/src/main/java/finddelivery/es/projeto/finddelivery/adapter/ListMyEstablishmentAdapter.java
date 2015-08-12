package finddelivery.es.projeto.finddelivery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import finddelivery.es.projeto.finddelivery.R;
import finddelivery.es.projeto.finddelivery.models.Establishment;

/**
 * Created by Vinicius on 29/07/2015.
 */
public class ListMyEstablishmentAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Establishment> items;
    private Context myContext;

    public ListMyEstablishmentAdapter(Context context, List<Establishment> items) {
        //Itens que preencheram o listview
        this.items = items;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
        myContext = context;
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
        //Pega o item de acordo com a posicao.
        Establishment item = items.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.activity_establishment_list, null);

        //atraves do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informacoes.
        ((TextView) view.findViewById(R.id.textViewNameRestaurant)).setText(item.getName());
        ((TextView) view.findViewById(R.id.textViewSpeciallity)).setText(item.getName());

        ((ImageView) view.findViewById(R.id.imageViewRestaurant)).setImageResource(R.mipmap.photodefault);

        return view;
    }
}
