package com.example.androidassignment3;

public class Movie {
    private final String posterUrl;
    private final String title;
    private final String year;

    public Movie(String url, String title, String year) {
        this.posterUrl = url;
        this.title = title;
        this.year = year;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }
}
