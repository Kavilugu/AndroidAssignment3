package com.example.androidassignment3;


import android.graphics.drawable.Drawable;
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
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = movies.get(position).getTitle();
        int year = movies.get(position).getYear();
        String url = movies.get(position).getPosterUrl();

        holder.title.setText(title);
        holder.year.setText(year);
        holder.poster.setImageDrawable(LoadImageFromWebOperations(url));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView poster;
        private TextView title;
        private TextView year;

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

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}
