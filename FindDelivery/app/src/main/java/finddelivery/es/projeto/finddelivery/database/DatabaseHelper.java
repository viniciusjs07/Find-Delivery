package finddelivery.es.projeto.finddelivery.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniela on 05/08/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private final static int VERSAO = 1;
    private final static String NOME = "FD_DATABASE";
    protected SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario (name VARCHAR( 20 ), login VARCHAR( 20 ) NOT NULL, password VARCHAR( 8 ), photo VARBINARY( 100 ));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    public SQLiteDatabase getDatabase() {
        if (database == null) {
            database = getWritableDatabase();
        }
        return database;
    }
}
