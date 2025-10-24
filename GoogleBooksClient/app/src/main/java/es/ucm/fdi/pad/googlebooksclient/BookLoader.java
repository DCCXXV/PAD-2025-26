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

// 1. Cambiado a List<BookInfo>
public class BookLoader extends AsyncTaskLoader<List<Bookinfo>> {

    private static final String LOG_TAG = BookLoader.class.getSimpleName();

    // 4. URL base corregida (sin ?q=)
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    // 4. Límite de resultados sugerido para depurar
    private static final String MAX_RESULTS = "10";
    private static final String KEY = BuildConfig.API_KEY; // Asegúrate de tener tu API Key en build.gradle(Module)

    // 2. Variables miembro para la query
    private String queryString;
    private String printType;

    // 2. Constructor actualizado para recibir los parámetros de búsqueda
    public BookLoader(@NonNull Context context, String queryString, String printType) {
        super(context);
        this.queryString = queryString;
        this.printType = printType;
    }

    @Nullable
    @Override
    public List<Bookinfo> loadInBackground() {
        // 1. Llama al método de red sin argumentos
        String jsonResult = null;
        try {
            jsonResult = getBookInfoJson();
        } catch (IOException e) {
            // Manejo de errores si falla la conexión
            e.printStackTrace();
            return null;
        }

        if (jsonResult == null) {
            return null;
        }

        // 2. Parsea el JSON y devuelve la lista de BookInfo
        return Bookinfo.fromJsonResponse(jsonResult);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad(); // Correcto según la práctica
    }

    // 4. Método de red (modificado)
    private String getBookInfoJson() throws IOException {

        HttpURLConnection conn = null;
        InputStream response = null;
        String resultString = null;

        try {
            // construir la URI para la petición
            Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("q", this.queryString)
                    .appendQueryParameter("maxResults", MAX_RESULTS)
                    .appendQueryParameter("printType", this.printType) // 4. Usar la variable printType
                    .appendQueryParameter("key", KEY)
                    .build();
            URL requestURL = new URL(builtURI.toString());

            // Log para depuración
            Log.d(LOG_TAG, "URL de la petición: " + requestURL.toString());

            // 4. conn es ahora una variable local
            conn = (HttpURLConnection) requestURL.openConnection();

            // se configura la conexion
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");

            //realizar conexion y obtener resultado
            conn.connect();
            int responseCode = conn.getResponseCode();

            if (responseCode == 200) { // HTTP_OK
                response = conn.getInputStream();
                resultString = convertIsToString(response);
                // Log para depuración
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
            // 4. Cerrar conexiones de forma segura
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

        return null; // Devuelve null si hubo un error
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