package es.ucm.fdi.pad.googlebooksclient;

import android.content.Context;
import android.net.Uri;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<String>>{

    final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    final String QUERY_PARAM = "q";
    final String MAX_RESULTS = "20";
    final String PRINT_TYPE = "all"; // revistas y libros
    final String KEY = "AIzaSyAmBxdY4CEab7FoFgjQr9KNcWr7wBvPo1I";

    private String queryString;
    private HttpURLConnection conn;

    public BookLoader(@NonNull Context context, String author) {
        super(context);
        queryString = "inauthor:\"" + author + "\"";
    }

    @Nullable
    @Override
    public List<String> loadInBackground() {

        List<String> data = new ArrayList<String>();

        // cargar los datos desde la red
        try {

            // construir la URI para la petición
            Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("q", queryString)
                    .appendQueryParameter("maxResults", MAX_RESULTS)
                    .appendQueryParameter("printType", PRINT_TYPE)
                    .appendQueryParameter("key", KEY)
                    .build();
            URL requestURL = new URL(builtURI.toString());

            // crear la HttpURLConnection
            conn = (HttpURLConnection) requestURL.openConnection();

            // se configura la conexion
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");

            //realizar conexion y obtener resultado
            conn.connect();
            int responseCode = conn.getResponseCode();

            InputStream response = conn.getInputStream();
            String resultString = convertIsToString(response);

        }catch (Exception e){

        } finally {
            conn.disconnect();
            /*if (is != null) {
                is.close();
            }*/
        }

        return data;
    }

    // transformar input en un string
    private String convertIsToString(InputStream stream)
            throws IOException, UnsupportedEncodingException {

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader (new InputStreamReader(stream));
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }

        if (builder.length() == 0) {
            return null;
        }

        return builder.toString();
    }

}
