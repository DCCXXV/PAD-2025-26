package es.ucm.fdi.pad.googlebooksclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.loader.app.LoaderManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText author;
    Button searchBtn;

    RadioGroup group;
    RadioButton authorRadio;
    RadioButton titleRadio;


    String queryString;
    String printType;

    final static private int BOOK_LOADER_ID = 1; // NI IDEA
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*EditText author = findViewById(R.id.authorText);
        String authorName = author.getText().toString().trim();

        Button searchBtn = findViewById(R.id.searchButton);

        BookLoader buscador = new BookLoader( this);

        searchBtn.setOnClickListener(v -> {
            String q = author.getText().toString().trim();
            buscador.loadInBackground();
        });*/

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID,null,
                    bookLoaderCallbacks);
        }
        
    }

    public void searchBooks(View view){

        // se obtiene la opcion de los radio buttons para printType
        RadioGroup group = findViewById(R.id.printTypeRadioGroup);
        int checkedId = group.getCheckedRadioButtonId();

        String printType;
        if (checkedId == R.id.radioBooks) {
            printType = "books";
        } else if (checkedId == R.id.radioMagazines) {
            printType = "magazines";
        } else {

            printType = "all";
        }

        /// TO DO: en queryString concatenar autores y titulo de DOS cajas de texto

        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID,
                queryBundle, bookLoaderCallbacks);
    }

    void updateBooksResultList(List<Bookinfo> books){
        BooksResultListAdapter adapter = new BooksResultListAdapter();
        adapter.setBooksData(books);
    }
}