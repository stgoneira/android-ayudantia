package cl.stgoneira.learning.android.applibros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.stgoneira.learning.android.applibros.db.AppContrato;
import cl.stgoneira.learning.android.applibros.db.AppDbHelper;
import cl.stgoneira.learning.android.applibros.modelo.Libro;
import cl.stgoneira.learning.android.applibros.recyclerview.LibroAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recupera libros BD
        AppDbHelper dbHelper    = new AppDbHelper(this);
        SQLiteDatabase db       = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(AppContrato.Libro.TABLE_NAME, null, null, null, null, null, null, null);
        List<Libro> libros = new ArrayList<Libro>();
        while( cursor.moveToNext()) {
            long libroId        = cursor.getLong(cursor.getColumnIndexOrThrow(AppContrato.Libro._ID));
            String libroTitulo  = cursor.getString( cursor.getColumnIndexOrThrow(AppContrato.Libro.COLUMN_NAME_TITULO) );
            String libroAutor   = cursor.getString( cursor.getColumnIndexOrThrow(AppContrato.Libro.COLUMN_NAME_AUTOR) );
            Libro libro         = new Libro(libroId, libroTitulo, libroAutor);
            libros.add(libro);
        }

        // RecyclerView
        RecyclerView rvLibros = (RecyclerView) findViewById(R.id.recyclerViewLibros);
        rvLibros.setLayoutManager(new LinearLayoutManager(this));
        LibroAdapter libroAdapter = new LibroAdapter( libros );
        rvLibros.setAdapter(libroAdapter);

        // controles del formulario
        EditText etId     = (EditText) findViewById(R.id.etId);
        EditText etTitulo = (EditText) findViewById(R.id.etTitulo);
        EditText etAutor  = (EditText) findViewById(R.id.etAutor);
        Button btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recupera datos ingresados por el usuario
                String titulo   = etTitulo.getText().toString();
                String autor    = etAutor.getText().toString();

                // Inserta en BD SQLite
                SQLiteDatabase db  = dbHelper.getWritableDatabase();
                ContentValues cvLibro = new ContentValues();
                cvLibro.put(AppContrato.Libro.COLUMN_NAME_TITULO, titulo);
                cvLibro.put(AppContrato.Libro.COLUMN_NAME_AUTOR, autor);
                long idInsertado = db.insert(AppContrato.Libro.TABLE_NAME, null, cvLibro);

                // NOTIFICAR AL RECYCLERVIEW
                Libro libroNuevo = new Libro(idInsertado, titulo, autor );
                libroAdapter.agregarLibro( libroNuevo );

                // NOTIFICAR AL USUARIO
                Toast.makeText(MainActivity.this, "Se insertó libro con ID "+idInsertado, Toast.LENGTH_LONG).show();
                // si quisiera ir a otra pantalla (Activity)
                //Intent intent = new Intent(MainActivity.this, OtraPantallaActivity.class);
                //intent.putExtra("mensaje", "Se insertó libro con ID "+idInsertado);
                //startActivity(intent);

            }
        });

        // eventos adapter
        libroAdapter.setListener(new LibroAdapter.OnItemClickListener() {
            @Override
            public void onEditarClick(Libro libro) {
                etId.setText(libro.getId().toString());
                etTitulo.setText(libro.getTitulo());
                etAutor.setText(libro.getAutor());
            }

            @Override
            public void onEliminarClick(Libro libro) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete(AppContrato.Libro.TABLE_NAME, AppContrato.Libro._ID + " = ?", new String[]{libro.getId().toString()});
                libroAdapter.removerLibro(libro);
            }
        });
    }
}