package finddelivery.es.projeto.finddelivery.database;

import finddelivery.es.projeto.finddelivery.models.Establishment;
import finddelivery.es.projeto.finddelivery.models.SpecialityType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acacio on 06/08/2015.
 */

public class DAOEstablishment extends DatabaseHelper {

    private final String TABLE = "estabelecimento";


    public DAOEstablishment(Context context) {
        super(context);
    }

    public void insert(Establishment establishment) throws Exception {

        ContentValues valuesInsert = new ContentValues();

        valuesInsert.put("restaurant", establishment.getName());
        valuesInsert.put("endereco", establishment.getAdress());
        valuesInsert.put("especialidade", establishment.getSpeciality());
        valuesInsert.put("horario", establishment.getBusinessHour());
        valuesInsert.put("phone1", establishment.getPhone1());
        valuesInsert.put("phone2", establishment.getPhone2());
        valuesInsert.put("logo", establishment.getPhoto());

        getDatabase().insert(TABLE, null, valuesInsert);
    }

    public void update(Establishment establishment) throws Exception {

        ContentValues valuesUpdate = new ContentValues();

        valuesUpdate.put("restaurant", establishment.getName());
        valuesUpdate.put("endereco", establishment.getAdress());
        valuesUpdate.put("especialidade", establishment.getSpeciality());
        valuesUpdate.put("horario", establishment.getBusinessHour());
        valuesUpdate.put("phone1", establishment.getPhone1());
        valuesUpdate.put("phone2", establishment.getPhone2());
        valuesUpdate.put("logo", establishment.getPhoto());

        getDatabase().update(TABLE, valuesUpdate, "restaurant = ?", new String[]{"" + establishment.getName()});

    }

    public List<Establishment> findAll() throws Exception {
        List<Establishment> establishments = new ArrayList<Establishment>();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            establishments.add(mountEstablishment(cursor));
            cursor.moveToNext();
        }
        return establishments;
    }


    public Establishment mountEstablishment(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        //Integer id = cursor.getInt(cursor.getColumnIndex("id"));
        String restaurante = cursor.getString(cursor.getColumnIndex("restaurant"));
        String endereco = cursor.getString(cursor.getColumnIndex("endereco"));
        String especialidade = cursor.getString(cursor.getColumnIndex("especialidade"));
        String horarioDeFuncionamento = cursor.getString(cursor.getColumnIndex("horario"));
        String fone1 = cursor.getString(cursor.getColumnIndex("phone1"));
        String fone2 = cursor.getString(cursor.getColumnIndex("phone2"));
        byte[] photo = cursor.getBlob(cursor.getColumnIndex("logo"));

        return new Establishment(restaurante, endereco,horarioDeFuncionamento, especialidade,fone1,fone2,photo);

    }

    public Establishment findByName(String restaurante) {
        String sql = "SELECT * FROM " + TABLE + " WHERE restaurant = ?";
        String[] selectionArgs = new String[] { restaurante};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        return mountEstablishment(cursor);
    }
}