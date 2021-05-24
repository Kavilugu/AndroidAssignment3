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
        this.browserFragment = new BrowserFragment();
        this.searchFragment = new SearchFragment();
        searchFragment.setController(this);
        this.mainActivity = mainActivity;
        mainActivity.fragmentTrans(searchFragment);
        this.rQueue = Volley.newRequestQueue(mainActivity.getApplicationContext());

    }

    public void movieSearchApi(String title) {
        Log.e("searcApi success", "in movieSearchApi, beginning");
        String url = "https://www.omdbapi.com/?apikey=" + MainActivity.OMDB_API_KEY + "&s=" + title;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("api-log", "api success");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Search");
                            ArrayList<Movie> movieArrayList = new ArrayList<>();

                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject currentJObj = jsonArray.getJSONObject(i);
                                Movie currentMovie = new Movie(currentJObj.getString("Poster"), currentJObj.getString("Title"), currentJObj.getInt("Year"));
                                movieArrayList.add(currentMovie);
                            }
                            searchFragment.setList(movieArrayList);
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    public void setImage(MovieAdapter.ViewHolder holder, Drawable drawable) {
        mainActivity.runOnUiThread(() -> holder.poster.setImageDrawable(drawable));
    }


}
