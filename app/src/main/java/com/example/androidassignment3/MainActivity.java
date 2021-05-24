package com.example.androidassignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public static final String NY_TIMES_API_KEY = "UPeQIM5GaD2LWH1hNUogB5wMxFIC0CIG";
    public static final String OMDB_API_KEY = "84237959";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragmentManager.beginTransaction();
        fragTrans.add(R.id.container, new SearchFragment());
        fragTrans.commit();
    }
}