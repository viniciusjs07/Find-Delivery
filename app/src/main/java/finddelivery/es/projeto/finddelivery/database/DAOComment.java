package finddelivery.es.projeto.finddelivery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;

import finddelivery.es.projeto.finddelivery.models.User;

/**
 * Created by Lucas on 16/09/2015.
 */
public class DAOComment extends DatabaseHelper {

    private final String TABLE = "comentario";

    public DAOComment(Context context) {
        super(context);
    }

    public void insert(String idUser, String idEstab, String comment) throws Exception {

        ContentValues valuesInsert = new ContentValues();

        valuesInsert.put("idUser", idUser);
        valuesInsert.put("idEstab", idEstab);
        valuesInsert.put("comentario", comment);

        getDatabase().insert(TABLE, null, valuesInsert);
    }

    public void update(String idUser, String idEstab, String comment) throws Exception {

        ContentValues valuesUpdate = new ContentValues();

        valuesUpdate.put("idUser", idUser);
        valuesUpdate.put("idEstab", idEstab);
        valuesUpdate.put("comentario", comment);

        getDatabase().update(TABLE, valuesUpdate, "idUser = ? AND idEstab = ? ", new String[]{idUser, idEstab});
    }

    public Map<User, String> searchCommentByEstablishment(String idEstab) throws Exception {
        Map<User, String> comments = new HashMap<User, String>();
        String sql = "SELECT * FROM comentario AS c INNER JOIN usuario AS u ON c.idUser = u.login WHERE c.idEstab = ?";
        String[] selectionArgs = new String[] { idEstab};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            comments.put(mountUsers(cursor), mountComments(cursor));
            cursor.moveToNext();
        }
        return comments;
    }

    private String mountComments(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }

        return cursor.getString(cursor.getColumnIndex("comentario"));
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
