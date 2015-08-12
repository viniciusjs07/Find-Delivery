package finddelivery.es.projeto.finddelivery.database;

import finddelivery.es.projeto.finddelivery.models.Establishment;
import finddelivery.es.projeto.finddelivery.models.SpecialityType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ac�cio on 06/08/2015.
 */

public class DAOEstablishment extends DatabaseHelper {

    private final String TABLE = "Estabelecimento";

    public  DAOEstablishment (Context context) {
        super(context);
    }

    public void insert(Establishment establishment) throws Exception {
        ContentValues values = new ContentValues();

        values.put("restaurante", establishment.getName());
        values.put("endereco", establishment.getAdress());
        values.put("especialidade", String.valueOf(establishment.getSpeciality()));
        values.put("horario de funcionamento", establishment.getBusinessHour());


        getDatabase().insert(TABLE, null, values);

    }

    public void update(Establishment establishment) throws Exception {
        ContentValues values = new ContentValues();

        values.put("restaurante", establishment.getName());
        values.put("endere�o", establishment.getAdress());
        values.put("especialidade", String.valueOf(establishment.getSpeciality()));
        values.put("horario de funcionamento", establishment.getBusinessHour());


        getDatabase().update(TABLE, values, "id = ?", new String[]{"" + establishment.getId()});
    }

    public Establishment findByName(String name) {

        String sql = "SELECT * FROM " + TABLE + " WHERE name = ?";
        String[] selectionArgs = new String[]{"" + name};
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
        String restaurante = cursor.getString(cursor.getColumnIndex("restaurante"));
        String endereco = cursor.getString(cursor.getColumnIndex("endereco"));
        SpecialityType especialidade = SpecialityType.valueOf(cursor.getString(cursor.getColumnIndex("especialidade")));
        String horarioDeFuncionamento = cursor.getString(cursor.getColumnIndex("horario de funcionamento"));

        return new Establishment(restaurante, endereco,horarioDeFuncionamento, especialidade,null,null);

    }

    public Establishment findByLogin(String restaurante) {
        String sql = "SELECT * FROM " + TABLE + " WHERE restaurante = ?";
        String[] selectionArgs = new String[] { restaurante};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        return montaEstabelecimento(cursor);
    }
}