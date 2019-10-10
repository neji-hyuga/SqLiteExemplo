package br.com.sqliteexemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private EditText nomeAlunoEditText;
    private Button okButton;
    private TextView listaAlunoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeAlunoEditText = findViewById(R.id.nome_edit_text_id);
        listaAlunoTextView = findViewById(R.id.lista_alunos_text_view);
        okButton = findViewById(R.id.ok_button_id);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gravarAluno();
                listarAlunos();
            }
        });

        listarAlunos();
    }

    private void gravarAluno() {

        // criar db helper
        AlunoDbHelper dbHelper = new AlunoDbHelper(this);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String nomeAluno = nomeAlunoEditText.getEditableText().toString();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AlunoContract.AlunoEntry.COLUMN_NAME_NOME, nomeAluno);


// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(AlunoContract.AlunoEntry.TABLE_NAME, null, values);

    }

    private void listarAlunos(){

        // criar db helper
        AlunoDbHelper dbHelper = new AlunoDbHelper(this);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();


// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                AlunoContract.AlunoEntry.COLUMN_NAME_NOME,
                        };

// Filter results WHERE "title" = 'My Title'
//        String selection = AlunoContract.AlunoEntry.COLUMN_NAME_NOME + " = ?";
//        String[] selectionArgs = { "JOE" };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                AlunoContract.AlunoEntry.COLUMN_NAME_NOME + " ASC";

        Cursor cursor = db.query(
                AlunoContract.AlunoEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List<String> listaNomes = new ArrayList<>();
        while(cursor.moveToNext()) {
            String nomeAluno = cursor.getString(
                    cursor.getColumnIndexOrThrow(AlunoContract.AlunoEntry.COLUMN_NAME_NOME));
            listaNomes.add(nomeAluno);
        }
        cursor.close();

        listaAlunoTextView.setText("");
        for (String nome : listaNomes) {
            listaAlunoTextView.setText(listaAlunoTextView.getText()+nome+"\n");
        }


    }
}
