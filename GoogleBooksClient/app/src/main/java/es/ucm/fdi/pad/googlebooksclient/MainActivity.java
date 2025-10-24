package es.ucm.fdi.pad.googlebooksclient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    final static private int BOOK_LOADER_ID = 1; // ID para el Loader

    // 1. Declaración de variables de clase para la UI y datos
    private EditText mTitleEditText;
    private EditText mAuthorEditText;
    private RadioGroup mPrintTypeRadioGroup;
    private TextView mStatusTextView;
    private RecyclerView mRecyclerView;
    private BookResultListAdapter mAdapter; // Necesitas implementar esta clase

    // El callbacks debe ser final y usar la clase corregida
    private final BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Inicialización de las vistas
        mTitleEditText = findViewById(R.id.titleEditText);
        mAuthorEditText = findViewById(R.id.authorEditText);
        mPrintTypeRadioGroup = findViewById(R.id.printTypeRadioGroup);
        mStatusTextView = findViewById(R.id.statusTextView);

        // 3. Configuración del RecyclerView y el Adaptador
        mRecyclerView = findViewById(R.id.recyclerView);

        // Inicializar el adaptador con una lista vacía
        mAdapter = new BookResultListAdapter(new ArrayList<Bookinfo>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el LoaderManager.
        // La condición de inicio sigue el patrón de la práctica (opcional, pero buena práctica)
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if (loaderManager.getLoader(BOOK_LOADER_ID) != null) {
            loaderManager.initLoader(BOOK_LOADER_ID, null, bookLoaderCallbacks);
        }

        // El resto de la lógica de RadioGroup y búsqueda se mueve a searchBooks()
    }

    /**
     * Método enlazado al botón de búsqueda (android:onClick="searchBooks" en el XML).
     */
    public void searchBooks(View view){
        // 4. Obtener datos de la UI en el momento del click
        String titleString = mTitleEditText.getText().toString().trim();
        String authorString = mAuthorEditText.getText().toString().trim();

        // Concatenar título y autor para la queryString
        String queryString = titleString;
        if (!authorString.isEmpty()) {
            // Formato: título + inauthor:autor. La API de Google Books lo maneja bien.
            queryString += (queryString.isEmpty() ? "" : " ") + "inauthor:\"" + authorString + "\"";
        }

        if (queryString.isEmpty()) {
            mStatusTextView.setText("¡Introduce un título y/o autor para buscar!");
            return; // No buscar si no hay texto
        }

        // Obtener el tipo de impresión (books, magazines, all)
        int checkedId = mPrintTypeRadioGroup.getCheckedRadioButtonId();
        String printType;
        if (checkedId == R.id.radioBooks) {
            printType = "books";
        } else if (checkedId == R.id.radioMagazines) {
            printType = "magazines";
        } else {
            printType = "all";
        }

        // 5. Configurar el Bundle y reiniciar el Loader
        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);

        // Mostrar estado "Cargando..."
        mStatusTextView.setText("Cargando...");
        mStatusTextView.setVisibility(View.VISIBLE);
        mAdapter.setBooksData(new ArrayList<>()); // Limpiar lista mientras carga

        // Iniciar/Reiniciar el Loader
        LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID,
                queryBundle, bookLoaderCallbacks);
    }

    /**
     * 6. Método para recibir los resultados del BookLoaderCallbacks
     */
    public void updateBooksResultList(List<Bookinfo> books) {
        // Ocultar el texto de estado por defecto
        mStatusTextView.setVisibility(View.VISIBLE);

        if (books != null && !books.isEmpty()) {
            // Mostrar resultados
            mStatusTextView.setText("Resultados: " + books.size());
            mAdapter.setBooksData(books);
        } else {
            // Mostrar no hay resultados
            mStatusTextView.setText("No se han encontrado resultados");
            mAdapter.setBooksData(new ArrayList<>()); // Asegurarse de que el adaptador está vacío
        }
    }

    @Override
    public void onBookClick(Bookinfo book) {
        if (book.getInfoLink() != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getInfoLink().toString()));

            startActivity(intent);
        } else {
            mStatusTextView.setText("Este libro no tiene enlace disponible.");
        }
    }

    // Aquí es donde debería ir la clase BooksResultListAdapter (Fase 5).
    // Si la pones como una clase externa, asegúrate de que tiene un constructor que funcione sin Context.
}