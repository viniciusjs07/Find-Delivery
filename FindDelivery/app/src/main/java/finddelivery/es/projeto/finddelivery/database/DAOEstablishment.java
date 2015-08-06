package finddelivery.es.projeto.finddelivery.database;

import finddelivery.es.projeto.finddelivery.models.Establishment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acácio on 06/08/2015.
 */
public class DAOEstablishment extends DatabaseHelper {

    private final String TABLE = 'Estabelecimento';

    public  DAOEstablishment (Context context) {
        super(context);
    }

    public void insert(Establishment establishment) throws Exception {
        ContentValues values = new ContentValues();

        values.put("restaurante", establishment.getName());
        values.put("endereco", establishment.getAdress());
        values.put("especialidade", establishment.getSpeciality());
        values.put("horario de funcionamento", establishment.getBusinessHour());


        getDatabase().insert(TABLE, null, values);

    }

    public void update(Establishment establishment) throws Exception {
        ContentValues values = new ContentValues();

        values.put("restaurante", establishment.getName());
        values.put("endereço", establishment.getAdress());
        values.put("especialidade", establishment.getSpeciality());
        values.put("horario de funcionamento", establishment.getBusinessHour());


        getDatabase().update(TABLE, values, "id = ?", new String[]{"" + establishment.getId()});
    }

    public Establishment findById(Integer id) {

        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
        String[] selectionArgs = new String[]{"" + id};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        return montaEstabelecimento(cursor);
    }

    public List<Establishment> findAll() throws Exception {
        List<Establishment> retorno = new ArrayList<Establishment>();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaEstabelecimento(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }

    public Establishment montaEstabelecimento(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex("id"));
        String restaurante = cursor.getString(cursor.getColumnIndex("Restaurante"));
        String endereco = cursor.getString(cursor.getColumnIndex("Endereco"));
        String especialidade = cursor.getString(cursor.getColumnIndex("Especialidade"));
        String horarioDeFuncionamento = cursor.getString(cursor.getColumnIndex("Horario de funcionamento"));

        return new Establishment(id, restaurante, endereco,especialidade, horarioDeFuncionamento);

    }

    public Establishment findByLogin(String restaurante) {
        String sql = "SELECT * FROM " + TABLE + " WHERE restaurante = ?";
        String[] selectionArgs = new String[] { restaurante};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        return montaEstabelecimento(cursor);
    }
}

