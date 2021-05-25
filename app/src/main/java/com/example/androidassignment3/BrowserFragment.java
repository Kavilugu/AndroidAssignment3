package com.example.androidassignment3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BrowserFragment extends Fragment {
    private final String url;
    private Controller controller;

    public BrowserFragment(String url) {
        this.url = url;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browser, container, false);

        WebView webView = view.findViewById(R.id.browserWebView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        Button btnBrowserReturn = view.findViewById(R.id.browserReturnButton);
        btnBrowserReturn.setOnClickListener(v -> controller.setSearchFragment());

        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public void setController (Controller controller) {
        this.controller = controller;
    }
}
