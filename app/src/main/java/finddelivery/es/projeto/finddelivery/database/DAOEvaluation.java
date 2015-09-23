package finddelivery.es.projeto.finddelivery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.models.User;

/**
 * Created by Lucas on 23/09/2015.
 */
public class DAOEvaluation extends DatabaseHelper {

    private final String TABLE = "avaliacao";

    public DAOEvaluation(Context context) {
        super(context);
    }

    public void insert(String idUser, String idEstab, Float grade) throws Exception {

        ContentValues valuesInsert = new ContentValues();

        valuesInsert.put("idUser", idUser);
        valuesInsert.put("idEstab", idEstab);
        valuesInsert.put("avaliacao", grade);

        getDatabase().insert(TABLE, null, valuesInsert);
    }

    public void update(String idUser, String idEstab, Float grade) throws Exception {

        ContentValues valuesUpdate = new ContentValues();

        valuesUpdate.put("idUser", idUser);
        valuesUpdate.put("idEstab", idEstab);
        valuesUpdate.put("comentario", grade);

        getDatabase().update(TABLE, valuesUpdate, "idUser = ? AND idEstab = ? ", new String[]{"" + idUser + "," + idEstab});
    }

    public Map<User, String> searchEvaluationByEstablishment(String idEstab) throws Exception {
        Map<User, String> evaluations = new HashMap<User, String>();
        String sql = "SELECT * FROM avaliacao AS c INNER JOIN usuario AS u ON c.idUser = u.login WHERE c.idEstab = ?";
        String[] selectionArgs = new String[] { idEstab};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            evaluations.put(mountUsers(cursor), mountEvaluations(cursor));
            cursor.moveToNext();
        }
        return evaluations;
    }

    private String mountEvaluations(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }

        return cursor.getString(cursor.getColumnIndex("avaliacao"));
    }

    private User mountUsers(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }

        String name = cursor.getString(cursor.getColumnIndex("name"));
        String login = cursor.getString(cursor.getColumnIndex("login"));
        String password = cursor.getString(cursor.getColumnIndex("password"));
        byte[] photo = cursor.getBlob(cursor.getColumnIndex("photo"));

        return new User(name, login, password, photo);
    }
}
