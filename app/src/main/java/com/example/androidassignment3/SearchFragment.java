package com.example.androidassignment3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private ArrayList<Movie> list= new ArrayList<>();
    private RecyclerView movieView;
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Movie movie1 = new Movie("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQWlxl5juhtA-DXFdUcH9I08oq0FjqmqIXUAMxsVbrDjzlWi8aQkLy8HQhb3j8fvxmWn-Dq9H-&usqp=CAc", "FILM", 1920);
        Movie movie2 = new Movie("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQWlxl5juhtA-DXFdUcH9I08oq0FjqmqIXUAMxsVbrDjzlWi8aQkLy8HQhb3j8fvxmWn-Dq9H-&usqp=CAc", "TEST", 1990);
        Movie movie3 = new Movie("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQWlxl5juhtA-DXFdUcH9I08oq0FjqmqIXUAMxsVbrDjzlWi8aQkLy8HQhb3j8fvxmWn-Dq9H-&usqp=CAc", "ABC", 2020);
        Movie movie4 = new Movie("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQWlxl5juhtA-DXFdUcH9I08oq0FjqmqIXUAMxsVbrDjzlWi8aQkLy8HQhb3j8fvxmWn-Dq9H-&usqp=CAc", "MOVIE", 2021);
        list.add(movie1);
        list.add(movie2);
        list.add(movie3);
        list.add(movie4);

        View v = inflater.inflate(R.layout.fragment_search, container, false);
        movieView = (RecyclerView) v.findViewById(R.id.movieView);
        movieView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        MovieAdapter adapter = new MovieAdapter(list);
        movieView.setAdapter(adapter);
        adapter.setController(controller);
        return v;
    }
}
