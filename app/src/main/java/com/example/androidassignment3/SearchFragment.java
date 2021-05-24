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

public class SearchFragment extends Fragment {
    private RecyclerView movieView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        movieView = (RecyclerView) v.findViewById(R.id.movieView);
        movieView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        RecyclerView.Adapter adapter = new MovieAdapter(null);
        movieView.setAdapter(adapter);
        return v;
    }
}
