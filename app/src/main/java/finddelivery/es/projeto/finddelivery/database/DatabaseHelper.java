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
        db.execSQL("CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR( 20 ) NOT NULL, login VARCHAR( 20 ) NOT NULL, password VARCHAR( 8 ) NOT NULL, photo VARBINARY( 100 ) NOT NULL);");
        db.execSQL("CREATE TABLE estabelecimento (id INTEGER PRIMARY KEY AUTOINCREMENT, restaurante VARCHAR( 20 ) NOT NULL, endereco VARCHAR( 20 ), especialidade VARCHAR( 20 ), horario_de_funcionamento VARCHAR( 20 ), phone1 VARCHAR( 15 ), phone2 VARCHAR( 15 ), logo VARBINARY( 100 ), idUser VARCHAR( 20 ));");

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
