package com.example.androidassignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final MainActivity mainActivity;
    private BrowserFragment browserFragment;
    private final SearchFragment searchFragment;
    private final RequestQueue rQueue;

    public Controller(MainActivity mainActivity) {
        this.browserFragment = new BrowserFragment("https://imdb.com");
        this.searchFragment = new SearchFragment();
        searchFragment.setController(this);

        this.mainActivity = mainActivity;
        mainActivity.fragmentTrans(searchFragment);
        this.rQueue = Volley.newRequestQueue(mainActivity.getApplicationContext());
    }

    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    public void set(String key, String value) {
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(mainActivity.getApplicationContext());
        SharedPreferences.Editor editor = sh.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void movieSearchApi(String title) {
        String url = "https://www.omdbapi.com/?apikey=" + MainActivity.OMDB_API_KEY + "&s=" + title;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("Search");
                        ArrayList<Movie> movieArrayList = new ArrayList<>();

                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject currentJObj = jsonArray.getJSONObject(i);
                            Movie currentMovie = new Movie(currentJObj.getString("Poster"), currentJObj.getString("Title"), currentJObj.getString("Year"));
                            movieArrayList.add(currentMovie);
                        }
                        searchFragment.setList(movieArrayList);
                    } catch (JSONException e) {
                        searchFragment.setList(new ArrayList<>());
                    }
                }, error -> Log.e("error response", error.toString()));
        rQueue.add(stringRequest);
    }

    public void articleSearchApi(String title, Controller controller) {
        String url = "https://api.nytimes.com/svc/movies/v2/reviews/search.json?query=" + title + "&api-key=" + MainActivity.NY_TIMES_API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    String articleUrl = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject currentObject = jsonArray.getJSONObject(i);
                            String arrayTitle = currentObject.getString("display_title").toLowerCase();
                            if (arrayTitle.equals(title.toLowerCase())) {
                                articleUrl = currentObject.getJSONObject("link").getString("url");
                                break;
                            }
                        }

                        if (articleUrl.equals("")) {
                            articleUrl = "https://c.tenor.com/Z6gmDPeM6dgAAAAC/dance-moves.gif";
                        }
                    } catch (JSONException e) {
                        articleUrl = "https://c.tenor.com/Z6gmDPeM6dgAAAAC/dance-moves.gif";
                    }

                    browserFragment = new BrowserFragment(articleUrl);
                    browserFragment.setController(controller);
                    mainActivity.fragmentTrans(browserFragment);
                    if (articleUrl.equals("https://c.tenor.com/Z6gmDPeM6dgAAAAC/dance-moves.gif")) {
                        noResultToast();
                    }
                }, error -> Log.e("error response", error.toString()));
        rQueue.add(stringRequest);
    }

    public void setImage(MovieAdapter.ViewHolder holder, Drawable drawable) {
        mainActivity.runOnUiThread(() -> holder.poster.setImageDrawable(drawable));
    }

    public void setSearchFragment() {
        mainActivity.fragmentTrans(searchFragment);
    }

    public void noResultToast() {
        Context context = mainActivity.getApplicationContext();
        CharSequence text = "No review found";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
