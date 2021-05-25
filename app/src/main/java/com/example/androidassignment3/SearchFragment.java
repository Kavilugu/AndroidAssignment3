package com.example.androidassignment3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private ArrayList<Movie> list= new ArrayList<>();
    private RecyclerView movieView;
    private Controller controller;
    private MovieAdapter adapter;
    private Button searchBtn;
    private EditText editTextSearch;
    private String sharedPrefStr;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        movieView = (RecyclerView) v.findViewById(R.id.movieView);
        movieView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        searchBtn = v.findViewById(R.id.buttonSearch);
        editTextSearch = v.findViewById(R.id.editTextSearch);
        adapter = new MovieAdapter(list);
        movieView.setAdapter(adapter);
        adapter.setController(controller);
        setOnClickListeners();
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
        sharedPrefStr = sh.getString("searchMovieList", "");
        if (sharedPrefStr.length() != 0) {
            ArrayList<Movie> arrayItems;
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Movie>>(){}.getType();
                arrayItems = gson.fromJson(sharedPrefStr, type);
                setList(arrayItems);
        }
        return v;
    }

    public void setList (ArrayList<Movie> arrList) {
        controller.setList("searchMovieList", arrList);
        adapter = new MovieAdapter(arrList);
        movieView.setAdapter(adapter);
        adapter.setController(controller);
    }

    private void setOnClickListeners() {
        searchBtn.setOnClickListener(view -> {
            controller.movieSearchApi(editTextSearch.getText().toString());
        });
    }
}
