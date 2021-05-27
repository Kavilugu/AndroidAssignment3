package com.example.androidassignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

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
    private ImageButton buttonSearch;
    private EditText editTextSearch;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        buttonSearch = view.findViewById(R.id.buttonSearch);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        movieView = (RecyclerView) view.findViewById(R.id.movieView);

        setOnClickListeners();

        movieView.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        adapter = new MovieAdapter(list);
        movieView.setAdapter(adapter);
        adapter.setController(controller);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext().getApplicationContext());
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
        buttonSearch.setOnClickListener(view -> performSearch());

        // Set On 'Enter'-click (Android Keyboard) send action:
        editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            // When 'Enter' or 'Send' is pressed do:
            // Set: 'android:imeOptions="actionSearch"' in EditText,
            // ("action..." determines EditorInfo.IME_ACTION_...)
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();

                // Hide Keyboard on enter:
                InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
                handled = true;
            }
            return handled;
        });
    }

    private void performSearch() {
        controller.movieSearchApi(editTextSearch.getText().toString());
    }
}
