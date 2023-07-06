package cl.stgoneira.learning.android.applibros.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Libros.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppContrato.Libro.TABLE_NAME + " (" +
                    AppContrato.Libro._ID + " INTEGER PRIMARY KEY," +
                    AppContrato.Libro.COLUMN_NAME_TITULO + " TEXT," +
                    AppContrato.Libro.COLUMN_NAME_AUTOR + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AppContrato.Libro.TABLE_NAME;

    public AppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

        // puedo insertar datos por defecto en la tabla aquí
        ContentValues libro1 = new ContentValues();
        libro1.put(AppContrato.Libro.COLUMN_NAME_TITULO, "Aprendiendo Android");
        libro1.put(AppContrato.Libro.COLUMN_NAME_AUTOR, "Iplacex");
        db.insert(AppContrato.Libro.TABLE_NAME, null, libro1 );
        // libro2
        ContentValues libro2 = new ContentValues();
        libro2.put(AppContrato.Libro.COLUMN_NAME_TITULO, "Aprendiendo Kotlin");
        libro2.put(AppContrato.Libro.COLUMN_NAME_AUTOR, "Iplacex");
        db.insert(AppContrato.Libro.TABLE_NAME, null, libro2 );

        // si quiero escribir código puro SQL, puedo usar
        // db.rawQuery()
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no es necesario de implementar
    }
}
