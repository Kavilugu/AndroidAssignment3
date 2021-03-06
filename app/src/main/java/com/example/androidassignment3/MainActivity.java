package com.example.androidassignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    public static final String NY_TIMES_API_KEY = "UPeQIM5GaD2LWH1hNUogB5wMxFIC0CIG";
    public static final String OMDB_API_KEY = "84237959";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Controller(this);
    }

    public void fragmentTrans(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragmentManager.beginTransaction();
        fragTrans.replace(R.id.container, fragment);
        fragTrans.commit();
    }

    @Override
    protected void onStop() {
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("searchMovieList", "");
        editor.apply();
        super.onStop();
    }
}
