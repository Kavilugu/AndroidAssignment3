package com.example.androidassignment3;


import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private ArrayList<Movie> movies;
    static Controller controller;

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = movies.get(position).getTitle();
        String year = movies.get(position).getYear();
        String url = movies.get(position).getPosterUrl();

        holder.title.setText(title);
        holder.year.setText(String.valueOf(year));
        ImageThread imageThread = new ImageThread(url, holder);
        imageThread.start();
        holder.id = position;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster;
        private TextView title;
        private TextView year;
        private int id;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            year = itemView.findViewById(R.id.textViewYear);
            poster = itemView.findViewById(R.id.imageViewPoster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
    private class ImageThread extends Thread {
        private String url;
        private ViewHolder holder;

        public ImageThread(String url, ViewHolder holder) {
            this.url = url;
            this.holder = holder;
        }

        public void run() {
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                controller.setImage(holder, d);
            } catch (Exception e) {
            }
        }
    }


}
