package es.ucm.fdi.pad.googlebooksclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

// 1. **CAMBIO CRUCIAL:** El tipo de retorno debe ser List<BookInfo>
//    para coincidir con el tipo de dato de BookLoader.
public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Bookinfo>> {

    // 2. Usamos una referencia a MainActivity (o su interfaz) para llamar a updateBooksResultList
    private final MainActivity mMainActivity;

    // Constantes para las claves del Bundle (opcional, pero buena práctica)
    public static final String EXTRA_QUERY = "queryString";
    public static final String EXTRA_PRINT_TYPE = "printType";

    // 3. El constructor debe recibir la Activity para tener acceso a sus métodos
    public BookLoaderCallbacks (MainActivity activity){
        this.mMainActivity = activity;
    }

    @NonNull
    @Override
    public Loader<List<Bookinfo>> onCreateLoader(int id, @Nullable Bundle args) {
        // 4. Extraer los parámetros de búsqueda del Bundle
        String queryString = null;
        String printType = "all"; // Valor por defecto

        if (args != null) {
            queryString = args.getString(EXTRA_QUERY);
            printType = args.getString(EXTRA_PRINT_TYPE);
        }

        // 5. Crear y devolver una nueva instancia de BookLoader, pasándole la query y el tipo
        return new BookLoader(mMainActivity, queryString, printType);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Bookinfo>> loader, List<Bookinfo> books) {
        // 6. Cuando la carga termina, se llama al método de la Activity
        //    para actualizar la RecyclerView.
        if (mMainActivity != null) {
            mMainActivity.updateBooksResultList(books);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Bookinfo>> loader) {
        // 7. Limpiar la lista de resultados cuando el Loader se reinicia.
        if (mMainActivity != null) {
            mMainActivity.updateBooksResultList(null);
        }
    }
}