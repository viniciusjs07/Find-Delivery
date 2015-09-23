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

    public  DAOEstablishment (Context context) {
        super(context);
    }

    public void insert(Establishment establishment, String idUser) throws Exception {

        ContentValues valuesInsert = new ContentValues();

        valuesInsert.put("restaurante", establishment.getName());
        valuesInsert.put("endereco", establishment.getAdress());
        valuesInsert.put("horario_de_funcionamento", establishment.getBusinessHour());
        valuesInsert.put("especialidade", establishment.getSpeciality());
        valuesInsert.put("phone1", establishment.getPhone1());
        valuesInsert.put("phone2", establishment.getPhone2());
        valuesInsert.put("logo", establishment.getPhoto());
        valuesInsert.put("idUser", idUser);

        getDatabase().insert(TABLE, null, valuesInsert);
    }

    public void update(Establishment establishment, String idUser) throws Exception {

        ContentValues valuesUpdate = new ContentValues();

        valuesUpdate.put("restaurante", establishment.getName());
        valuesUpdate.put("endereco", establishment.getAdress());
        valuesUpdate.put("horario_de_funcionamento", establishment.getBusinessHour());
        valuesUpdate.put("especialidade", establishment.getSpeciality());
        valuesUpdate.put("phone1", establishment.getPhone1());
        valuesUpdate.put("phone2", establishment.getPhone2());
        valuesUpdate.put("logo", establishment.getPhoto());
        valuesUpdate.put("idUser", idUser);


        getDatabase().update(TABLE, valuesUpdate, "restaurante = ?", new String[]{establishment.getName()});
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

        String restaurante = cursor.getString(cursor.getColumnIndex("restaurante"));
        String endereco = cursor.getString(cursor.getColumnIndex("endereco"));
        String horarioDeFuncionamento = cursor.getString(cursor.getColumnIndex("horario_de_funcionamento"));
        String especialidade = cursor.getString(cursor.getColumnIndex("especialidade"));
        String fone1 = cursor.getString(cursor.getColumnIndex("phone1"));
        String fone2 = cursor.getString(cursor.getColumnIndex("phone2"));
        byte[] photo = cursor.getBlob(cursor.getColumnIndex("logo"));

        return new Establishment(restaurante, endereco,horarioDeFuncionamento, especialidade,fone1,fone2,photo);

    }

    public Establishment findByName(String restaurante) {
        String sql = "SELECT * FROM " + TABLE + " WHERE restaurante = ?";
        String[] selectionArgs = new String[] { restaurante};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        return mountEstablishment(cursor);
    }

    public List<Establishment> findByUser(String idUser) throws Exception {
        List<Establishment> establishments = new ArrayList<Establishment>();
        String sql = "SELECT * FROM " + TABLE + " WHERE idUser = ?";
        String[] selectionArgs = new String[] {idUser};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            establishments.add(mountEstablishment(cursor));
            cursor.moveToNext();
        }
        return establishments;

    }
}