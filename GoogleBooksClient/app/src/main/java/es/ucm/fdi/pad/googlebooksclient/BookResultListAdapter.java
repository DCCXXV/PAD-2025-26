package es.ucm.fdi.pad.googlebooksclient;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookResultListAdapter extends RecyclerView.Adapter<BookResultListAdapter.ViewHolder> {

    private List<Bookinfo> mBooksData;
    private final OnBookClickListener mListener; // Nuevo campo para el listener

    /**
     * Constructor modificado para recibir el Listener.
     */
    public BookResultListAdapter(List<Bookinfo> data, OnBookClickListener listener) {
        this.mBooksData = data;
        this.mListener = listener; // Inicializar el listener
    }

    // ... el resto del código del adaptador ...

    // --- INNER STATIC CLASS: ViewHolder ---
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Vistas
        private final TextView titleTextView;
        private final TextView authorsTextView;
        private final TextView infoLinkTextView;

        // El Listener y el libro actual se declaran aquí
        private final OnBookClickListener listener;
        private Bookinfo currentBook; // Referencia al libro que se enlaza

        // El constructor ahora recibe el listener
        public ViewHolder(View itemView, OnBookClickListener listener) {
            super(itemView);
            this.listener = listener; // Guardar el listener

            titleTextView = itemView.findViewById(R.id.itemTitle);
            authorsTextView = itemView.findViewById(R.id.itemAuthors);
            infoLinkTextView = itemView.findViewById(R.id.itemInfoLink);

            itemView.setOnClickListener(this);
        }

        // Nuevo método para enlazar el libro (llamado desde onBindViewHolder)
        public void bind(Bookinfo book) {
            this.currentBook = book;
            // Aquí iría el resto de la lógica de enlazado, pero la dejamos en onBindViewHolder por ahora.
        }

        @Override
        public void onClick(View view) {
            // Ya no accedemos a mBooksData; usamos el libro enlazado
            if (currentBook != null && listener != null) {
                listener.onBookClick(currentBook);
            }
        }
    }
    // --------------------------------

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list_item, parent, false);
        // Pasar el listener al ViewHolder al crearlo
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bookinfo currentBook = mBooksData.get(position);

        // 1. IMPORTANTE: Llamar al método bind() en el ViewHolder
        holder.bind(currentBook);

        // 2. Lógica de rellenar vistas (igual que antes)
        holder.titleTextView.setText(currentBook.getTitle());

        // ... el resto de la lógica de visibilidad y texto ...
        String authors = currentBook.getAuthors();
        if (authors != null && !authors.isEmpty()) {
            holder.authorsTextView.setText("Autor(es): " + authors);
            holder.authorsTextView.setVisibility(View.VISIBLE);
        } else {
            holder.authorsTextView.setVisibility(View.GONE);
        }

        if (currentBook.getInfoLink() != null) {
            holder.infoLinkTextView.setText(currentBook.getInfoLink().toString());
            holder.infoLinkTextView.setVisibility(View.VISIBLE);
        } else {
            holder.infoLinkTextView.setVisibility(View.GONE);
        }
    }

    // Devolver el número total de items
    @Override
    public int getItemCount() {
        return mBooksData.size();
    }

    /**
     * Método público para actualizar los datos. (Requerido por la práctica)
     * @param data La nueva lista de BookInfo.
     */
    public void setBooksData(List<Bookinfo> data) {
        // Usar la referencia pasada o una lista vacía si es null
        this.mBooksData = (data != null) ? data : new ArrayList<>();
        // Notificar al RecyclerView que la lista ha cambiado
        notifyDataSetChanged();
    }
}

