package es.ucm.fdi.pad.googlebooksclient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnBookClickListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    final static private int BOOK_LOADER_ID = 1;

    private EditText mTitleEditText;
    private EditText mAuthorEditText;
    private RadioGroup mPrintTypeRadioGroup;
    private TextView mStatusTextView;
    private RecyclerView mRecyclerView;
    private BookResultListAdapter mAdapter;

    private final BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleEditText = findViewById(R.id.titleEditText);
        mAuthorEditText = findViewById(R.id.authorEditText);
        mPrintTypeRadioGroup = findViewById(R.id.printTypeRadioGroup);
        mStatusTextView = findViewById(R.id.statusTextView);

        mRecyclerView = findViewById(R.id.recyclerView);

        mAdapter = new BookResultListAdapter(new ArrayList<BookInfo>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if (loaderManager.getLoader(BOOK_LOADER_ID) != null) {
            loaderManager.initLoader(BOOK_LOADER_ID, null, bookLoaderCallbacks);
        }

    }

    /**
     * Método enlazado al botón de búsqueda (android:onClick="searchBooks" en el XML).
     */
    public void searchBooks(View view){
        String titleString = mTitleEditText.getText().toString().trim();
        String authorString = mAuthorEditText.getText().toString().trim();

        String queryString = titleString;
        if (!authorString.isEmpty()) {
            queryString += (queryString.isEmpty() ? "" : " ") + "inauthor:\"" + authorString + "\"";
        }

        if (queryString.isEmpty()) {
            mStatusTextView.setText(R.string.introduce_un_t_tulo_y_o_autor_para_buscar);
            return;
        }

        int checkedId = mPrintTypeRadioGroup.getCheckedRadioButtonId();
        String printType;
        if (checkedId == R.id.radioBooks) {
            printType = "books";
        } else if (checkedId == R.id.radioMagazines) {
            printType = "magazines";
        } else {
            printType = "all";
        }

        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);

        mStatusTextView.setText(R.string.cargando);
        mStatusTextView.setVisibility(View.VISIBLE);
        mAdapter.setBooksData(new ArrayList<>());

        LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID,
                queryBundle, bookLoaderCallbacks);
    }

    /**
     * Método para recibir los resultados del BookLoaderCallbacks
     */
    public void updateBooksResultList(List<BookInfo> books) {
        mStatusTextView.setVisibility(View.VISIBLE);

        if (books != null && !books.isEmpty()) {
            mStatusTextView.setText(getString(R.string.resultados) + books.size());
            mAdapter.setBooksData(books);
        } else {
            mStatusTextView.setText(R.string.no_se_han_encontrado_resultados);
            mAdapter.setBooksData(new ArrayList<>());
        }
    }

    @Override
    public void onBookClick(BookInfo book) {
        if (book.getInfoLink() != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getInfoLink().toString()));

            startActivity(intent);
        } else {
            mStatusTextView.setText(R.string.este_libro_no_tiene_enlace_disponible);
        }
    }
}