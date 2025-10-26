package es.ucm.fdi.pad.googlebooksclient;

import android.content.Context;
import android.net.Uri;
import android.util.Log; // Importante añadir Log

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    private static final String LOG_TAG = BookLoader.class.getSimpleName();

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String MAX_RESULTS = "10";
    private static final String KEY = BuildConfig.API_KEY;
    private String queryString;
    private String printType;
    public BookLoader(@NonNull Context context, String queryString, String printType) {
        super(context);
        this.queryString = queryString;
        this.printType = printType;
    }

    @Nullable
    @Override
    public List<BookInfo> loadInBackground() {
        String jsonResult = null;
        try {
            jsonResult = getBookInfoJson();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (jsonResult == null) {
            return null;
        }

        return BookInfo.fromJsonResponse(jsonResult);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    private String getBookInfoJson() throws IOException {

        HttpURLConnection conn = null;
        InputStream response = null;
        String resultString = null;

        try {
            Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("q", this.queryString)
                    .appendQueryParameter("maxResults", MAX_RESULTS)
                    .appendQueryParameter("printType", this.printType)
                    .appendQueryParameter("key", KEY)
                    .build();
            URL requestURL = new URL(builtURI.toString());

            Log.d(LOG_TAG, "URL de la petición: " + requestURL.toString());

            conn = (HttpURLConnection) requestURL.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");

            conn.connect();
            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                response = conn.getInputStream();
                resultString = convertIsToString(response);
                Log.d(LOG_TAG, "JSON devuelto (primeros 500 chars): " +
                        (resultString.length() > 500 ? resultString.substring(0, 500) : resultString));
            } else {
                Log.e(LOG_TAG, "Error en la conexión, código: " + responseCode);
            }
            return resultString;

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error al obtener el JSON: ", e);
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }


    private String convertIsToString(InputStream stream)
            throws IOException, UnsupportedEncodingException {

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }

        if (builder.length() == 0) {
            return null;
        }

        return builder.toString();
    }
}