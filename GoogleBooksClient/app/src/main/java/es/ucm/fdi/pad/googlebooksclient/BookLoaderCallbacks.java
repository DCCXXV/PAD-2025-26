package es.ucm.fdi.pad.googlebooksclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<String>>{

    private Context context;

    static final String EXTRA_QUERY = "EXTRA_QUERY";
    static final String EXTRA_PRINT_TYPE = "EXTRA_PRINT_TYPE";

    public BookLoaderCallbacks (Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public Loader<List<String>> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader(context);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<String>> loader, List<String> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<String>> loader) {

    }
}
