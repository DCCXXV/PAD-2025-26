package es.ucm.fdi.pad.googlebooksclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Bookinfo>> {

    private final MainActivity mMainActivity;

    public static final String EXTRA_QUERY = "queryString";
    public static final String EXTRA_PRINT_TYPE = "printType";

    public BookLoaderCallbacks (MainActivity activity){
        this.mMainActivity = activity;
    }

    @NonNull
    @Override
    public Loader<List<Bookinfo>> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = null;
        String printType = "all";

        if (args != null) {
            queryString = args.getString(EXTRA_QUERY);
            printType = args.getString(EXTRA_PRINT_TYPE);
        }

        return new BookLoader(mMainActivity, queryString, printType);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Bookinfo>> loader, List<Bookinfo> books) {
        if (mMainActivity != null) {
            mMainActivity.updateBooksResultList(books);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Bookinfo>> loader) {
        if (mMainActivity != null) {
            mMainActivity.updateBooksResultList(null);
        }
    }
}