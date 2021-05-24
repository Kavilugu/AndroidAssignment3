package com.example.androidassignment3;

public class Movie {
    private String posterUrl;
    private String title;
    private String year;

    public Movie(String url, String title, String year) {
        this.posterUrl = url;
        this.title = title;
        this.year = year;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
