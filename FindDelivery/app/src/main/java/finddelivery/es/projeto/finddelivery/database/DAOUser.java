package finddelivery.es.projeto.finddelivery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import finddelivery.es.projeto.finddelivery.models.User;

/**
 * Created by Daniela on 05/08/2015.
 */
    public class DAOUser extends DatabaseHelper {

    private final String TABLE = "usuario";

    public DAOUser(Context context) {
        super(context);
    }

    public void insert(User user) throws Exception {
        ContentValues values = new ContentValues();

        values.put("usuario", user.getLogin());
        values.put("senha", user.getPassword());

        getDatabase().insert(TABLE, null, values);
    }

    public void update(User user) throws Exception {
        ContentValues values = new ContentValues();

        values.put("usuario", user.getLogin());
        values.put("senha", user.getPassword());

        getDatabase().update(TABLE, values, "id = ?", new String[]{"" + user.getId()});
    }

    public User findById(Integer id) {

        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
        String[] selectionArgs = new String[]{"" + id};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        return montaUsuario(cursor);
    }

    public List<User> findAll() throws Exception {
        List<User> retorno = new ArrayList<User>();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaUsuario(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }

    public User montaUsuario(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex("id"));
        String usuario = cursor.getString(cursor.getColumnIndex("usuario"));
        String senha = cursor.getString(cursor.getColumnIndex("senha"));

        return new User(id, usuario, senha);

    }


    public User findByLogin(String usuario, String senha) {
        String sql = "SELECT * FROM " + TABLE + " WHERE usuario = ? AND senha = ?";
        String[] selectionArgs = new String[] { usuario, senha };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        return montaUsuario(cursor);
    }
    }
