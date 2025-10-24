package es.ucm.fdi.pad.googlebooksclient;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.ViewHolder>{

    private ArrayList<Bookinfo> mBooksData = new ArrayList<>();

    // ViewHolder interno
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Ejemplo de vistas del item (ajusta a tu layout)
        TextView titleTextView;
        TextView authorTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    @NonNull
    @Override
    public BooksResultListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksResultListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setBooksData(List<Bookinfo> data){
        mBooksData = (ArrayList<Bookinfo>) data;
        notifyDataSetChanged();
    }
}
