package com.example.androidassignment3;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Controller {
    private MainActivity mainActivity;
    private BrowserFragment browserFragment;
    private SearchFragment searchFragment;
    private RequestQueue rQueue;

    public Controller(MainActivity mainActivity) {
        this.browserFragment = new BrowserFragment("https://imdb.com");
        this.searchFragment = new SearchFragment();
        searchFragment.setController(this);
        this.mainActivity = mainActivity;
        mainActivity.fragmentTrans(searchFragment);
        this.rQueue = Volley.newRequestQueue(mainActivity.getApplicationContext());

    }

    public void movieSearchApi(String title) {
        String url = "https://www.omdbapi.com/?apikey=" + MainActivity.OMDB_API_KEY + "&s=" + title;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error response", error.toString());
            }
        });
        rQueue.add(stringRequest);
    }

    public void articleSearchApi(String title) {
        String url = "https://api.nytimes.com/svc/movies/v2/reviews/search.json?query=" + title + "&api-key=" + MainActivity.NY_TIMES_API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String articleUrl = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");

                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject currentObject = jsonArray.getJSONObject(i);
                                    String arrayTitle = currentObject.getString("display_title").toLowerCase();
                                    if (arrayTitle.equals(title.toLowerCase())) {
                                        articleUrl = currentObject.getJSONObject("link").getString("url");
                                        break;
                                    }
                                }
                                if (articleUrl.equals("")) {
                                    articleUrl = jsonArray.getJSONObject(0).getJSONObject("link").getString("url");
                                }
                            }
                            else {
                                articleUrl = "https://c.tenor.com/Z6gmDPeM6dgAAAAC/dance-moves.gif";
                            }

                        } catch (JSONException e) {
                            articleUrl = "https://c.tenor.com/Z6gmDPeM6dgAAAAC/dance-moves.gif";
                        }
                        browserFragment = new BrowserFragment(articleUrl);
                        mainActivity.fragmentTrans(browserFragment);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error response", error.toString());
            }
        });
        rQueue.add(stringRequest);
    }

    public void setImage(MovieAdapter.ViewHolder holder, Drawable drawable) {
        mainActivity.runOnUiThread(() -> holder.poster.setImageDrawable(drawable));
    }


}
