package es.ucm.fdi.pad.googlebooksclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookResultListAdapter extends RecyclerView.Adapter<BookResultListAdapter.ViewHolder> {

    private List<BookInfo> mBooksData;
    private final OnBookClickListener mListener;

    /**
     * Constructor modificado para recibir el Listener.
     */
    public BookResultListAdapter(List<BookInfo> data, OnBookClickListener listener) {
        this.mBooksData = data;
        this.mListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView titleTextView;
        private final TextView authorsTextView;
        private final TextView infoLinkTextView;

        private final OnBookClickListener listener;
        private BookInfo currentBook;

        public ViewHolder(View itemView, OnBookClickListener listener) {
            super(itemView);
            this.listener = listener;

            titleTextView = itemView.findViewById(R.id.itemTitle);
            authorsTextView = itemView.findViewById(R.id.itemAuthors);
            infoLinkTextView = itemView.findViewById(R.id.itemInfoLink);

            itemView.setOnClickListener(this);
        }

        public void bind(BookInfo book) {
            this.currentBook = book;
        }

        @Override
        public void onClick(View view) {
            if (currentBook != null && listener != null) {
                listener.onBookClick(currentBook);
            }
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookInfo currentBook = mBooksData.get(position);

        holder.bind(currentBook);

        holder.titleTextView.setText(currentBook.getTitle());

        String authors = currentBook.getAuthors();
        if (authors != null && !authors.isEmpty()) {
            holder.authorsTextView.setText(holder.itemView.getContext().getString(R.string.autor_es) + authors);
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
    @Override
    public int getItemCount() {
        return mBooksData.size();
    }
    public void setBooksData(List<BookInfo> data) {
        this.mBooksData = (data != null) ? data : new ArrayList<>();
        notifyDataSetChanged();
    }
}

