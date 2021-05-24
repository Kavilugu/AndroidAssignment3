package com.example.androidassignment3;

import android.graphics.drawable.Drawable;

public class Controller {
    private MainActivity mainActivity;
    private BrowserFragment browserFragment;
    private SearchFragment searchFragment;

    public Controller(MainActivity mainActivity) {
        this.browserFragment = new BrowserFragment();
        this.searchFragment = new SearchFragment();
        searchFragment.setController(this);
        this.mainActivity = mainActivity;
        mainActivity.fragmentTrans(searchFragment);
    }

    public void setImage(MovieAdapter.ViewHolder holder, Drawable drawable) {
        mainActivity.runOnUiThread(() -> holder.poster.setImageDrawable(drawable));

    }


}
