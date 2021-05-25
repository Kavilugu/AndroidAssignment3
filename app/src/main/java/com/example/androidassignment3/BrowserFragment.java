package com.example.androidassignment3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BrowserFragment extends Fragment {
    private WebView webView;
    private String url;
    public BrowserFragment(String url) {
        this.url = url;
    }
    private Controller controller;
    private Button btnBrowserReturn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_browser, container, false);
        webView = v.findViewById(R.id.browserWebView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        btnBrowserReturn = v.findViewById(R.id.browserReturnButton);
        btnBrowserReturn.setOnClickListener(v1 -> {
            controller.setSearchFragment();
        });
        super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }

    public void setController (Controller controller) {
        this.controller = controller;
    }



}
