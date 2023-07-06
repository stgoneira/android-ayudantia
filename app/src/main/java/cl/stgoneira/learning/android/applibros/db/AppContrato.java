package cl.stgoneira.learning.android.applibros.db;

import android.provider.BaseColumns;

public class AppContrato {

    // constructor privado
    private AppContrato() {}

    /* Inner class that defines the table contents */
    public static class Libro implements BaseColumns {
        public static final String TABLE_NAME           = "libros";
        public static final String COLUMN_NAME_TITULO   = "titulo";
        public static final String COLUMN_NAME_AUTOR    = "autor";
    }
}
