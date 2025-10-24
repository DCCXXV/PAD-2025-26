package es.ucm.fdi.pad.googlebooksclient;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Bookinfo {

    private static final String LOG_TAG = Bookinfo.class.getSimpleName();

    private final String title;
    private final String authors;
    private final URL infoLink;


    public Bookinfo(String title, String authors, URL infoLink) {
        this.title = title;
        this.authors = authors;
        this.infoLink = infoLink;
    }

    //Getters

    public String getTitle(){
        return title;
    }

    public String getAuthors(){

        if(authors != null)return authors;
        else return "";
    }

    public URL getInfoLink(){
        return infoLink;
    }


    public static List<Bookinfo> fromJsonResponse(String jsonResponseString) {
        ArrayList<Bookinfo> books = new ArrayList<>();

        if (jsonResponseString == null || jsonResponseString.isEmpty()) {
            return books;
        }

        try {
            JSONObject root = new JSONObject(jsonResponseString);

            // Si no hay "items", es que no hay resultados
            if (!root.has("items")) {
                Log.w(LOG_TAG, "El JSON no contiene 'items'");
                return books;
            }

            JSONArray itemsArray = root.getJSONArray("items");

            // Recorrer todos los libros/revistas en el array "items"
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject bookItem = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = bookItem.getJSONObject("volumeInfo");

                // 1. Obtener el Título
                String title = volumeInfo.optString("title");

                // 2. Obtener Autores (si existen) [cite: 9]
                String authors = null;
                if (volumeInfo.has("authors")) {
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    StringBuilder authorsBuilder = new StringBuilder();
                    for (int j = 0; j < authorsArray.length(); j++) {
                        if (j > 0) {
                            authorsBuilder.append(", ");
                        }
                        authorsBuilder.append(authorsArray.getString(j));
                    }
                    authors = authorsBuilder.toString();
                }

                // 3. Obtener la URL
                URL infoLink = null;
                String urlString = volumeInfo.optString("infoLink");
                if (!urlString.isEmpty()) {
                    try {
                        infoLink = new URL(urlString);
                    } catch (MalformedURLException e) {
                        Log.e(LOG_TAG, "URL mal formada: " + urlString, e);
                    }
                }

                // Añadir el libro a la lista
                if (!title.isEmpty()) {
                    books.add(new Bookinfo(title, authors, infoLink));
                }
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error al parsear el JSON", e);
        }

        return books;
    }

}
