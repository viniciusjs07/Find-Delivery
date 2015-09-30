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

        values.put("name", user.getName());
        values.put("login", user.getLogin());
        values.put("password", user.getPassword());
        values.put("photo", user.getPhoto());

        getDatabase().insert(TABLE, null, values);
    }

    public void update(User user) throws Exception {
        ContentValues values = new ContentValues();

        values.put("name", user.getName());
        values.put("login", user.getLogin());
        values.put("password", user.getPassword());
        values.put("photo", user.getPhoto());

        getDatabase().update(TABLE, values, "login = ?", new String[]{user.getLogin()});
    }

    public void delete(String login) throws Exception{
        String table = "usuario";
        String whereClause = "login" + "=?";
        String[] whereArgs = new String[] { String.valueOf(login) };
        getDatabase().delete(table, whereClause, whereArgs);
    }

    public User findById(String login) { //Id user  = login

        String sql = "SELECT * FROM " + TABLE + " WHERE login = ?";
        String[] selectionArgs = new String[]{"" + login};
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

        String name = cursor.getString(cursor.getColumnIndex("name"));
        String login = cursor.getString(cursor.getColumnIndex("login"));
        String password = cursor.getString(cursor.getColumnIndex("password"));
        byte[] photo = cursor.getBlob(cursor.getColumnIndex("photo"));
       // return new User(name, login, password);
        return new User(name, login, password, photo);

    }


    public User findByLogin(String login, String password) {
        String sql = "SELECT * FROM " + TABLE + " WHERE login = ? AND password = ?";
        String[] selectionArgs = new String[] { login, password };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        return mountUser(cursor);
    }
}