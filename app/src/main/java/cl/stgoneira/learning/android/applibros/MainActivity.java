package cl.stgoneira.learning.android.applibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cl.stgoneira.learning.android.applibros.db.AppContrato;
import cl.stgoneira.learning.android.applibros.db.AppDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etTitulo = (EditText) findViewById(R.id.etTitulo);
        EditText etAutor  = (EditText) findViewById(R.id.etAutor);

        Button btnGuardar = (Button) findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // INSERCION EN BD
                String titulo   = etTitulo.getText().toString();
                String autor    = etAutor.getText().toString();
                AppDbHelper helper = new AppDbHelper(MainActivity.this);
                SQLiteDatabase db  = helper.getWritableDatabase();
                ContentValues libro = new ContentValues();
                libro.put(AppContrato.Libro.COLUMN_NAME_TITULO, titulo);
                libro.put(AppContrato.Libro.COLUMN_NAME_AUTOR, autor);
                long idInsertado = db.insert(AppContrato.Libro.TABLE_NAME, null, libro);

                // NOTIFICAR AL USUARIO
                Toast.makeText(MainActivity.this, "Se insertó libro con ID "+idInsertado, Toast.LENGTH_LONG).show();
                // si quisiera ir a otra pantalla (Activity)
                //Intent intent = new Intent(MainActivity.this, OtraPantallaActivity.class);
                //intent.putExtra("mensaje", "Se insertó libro con ID "+idInsertado);
                //startActivity(intent);
            }
        });
    }
}