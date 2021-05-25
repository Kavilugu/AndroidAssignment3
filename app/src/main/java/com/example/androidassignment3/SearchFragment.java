package com.example.androidassignment3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import java.util.Objects;

public class SearchFragment extends Fragment {
    private final ArrayList<Movie> list= new ArrayList<>();
    private RecyclerView movieView;
    private Controller controller;
    private MovieAdapter adapter;
    private Button searchBtn;
    private EditText editTextSearch;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchBtn = view.findViewById(R.id.buttonSearch);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        movieView = (RecyclerView) view.findViewById(R.id.movieView);

        movieView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext()));
        adapter = new MovieAdapter(list);
        movieView.setAdapter(adapter);
        adapter.setController(controller);
        setOnClickListeners();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getContext()).getApplicationContext());
        String sharedPrefStr = sharedPref.getString("searchMovieList", "");
        if (sharedPrefStr.length() != 0) {
            ArrayList<Movie> arrayItems;
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Movie>>(){}.getType();
                arrayItems = gson.fromJson(sharedPrefStr, type);
                setList(arrayItems);
        }

        return view;
    }

    public void setList (ArrayList<Movie> arrList) {
        controller.setList("searchMovieList", arrList);
        adapter = new MovieAdapter(arrList);
        movieView.setAdapter(adapter);
        adapter.setController(controller);
    }

    private void setOnClickListeners() {
        searchBtn.setOnClickListener(view -> controller.movieSearchApi(editTextSearch.getText().toString()));
    }
}
