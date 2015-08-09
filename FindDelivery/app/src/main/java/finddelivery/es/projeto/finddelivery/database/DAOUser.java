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

        values.put("login", user.getLogin());
        values.put("password", user.getPassword());

        getDatabase().insert(TABLE, null, values);
    }

    public void update(User user) throws Exception {
        ContentValues values = new ContentValues();

        values.put("login", user.getLogin());
        values.put("password", user.getPassword());

        getDatabase().update(TABLE, values, "id = ?", new String[]{"" + user.getId()});
    }

    public User findById(Integer id) {

        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
        String[] selectionArgs = new String[]{"" + id};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        return mountUser(cursor);
    }

    public List<User> findAll() throws Exception {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            users.add(mountUser(cursor));
            cursor.moveToNext();
        }
        return users;
    }

    public User mountUser(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex("id"));
        String login = cursor.getString(cursor.getColumnIndex("login"));
        String password = cursor.getString(cursor.getColumnIndex("password"));

        return new User(id, login, password);
    }


    public User findByLogin(String login, String password) {
        String sql = "SELECT * FROM " + TABLE + " WHERE login = ? AND password = ?";
        String[] selectionArgs = new String[] { login, password };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        return mountUser(cursor);
    }
}