package com.appcia.moviemap;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieInfo {

    @SerializedName("page")
    private int pageNo;

    @SerializedName("total_results")
    private int total_result;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("results")
    private ArrayList<MovieData> mMovieData;


    public int getPageNo() {
        return pageNo;
    }

    public int getTotal_result() {
        return total_result;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public ArrayList<MovieData> getMovieData() {
        return mMovieData;
    }
}

class MovieData{
    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("id")
    private int id;

    @SerializedName("video")
    private String video;

    @SerializedName("vote_average")
    private String rating;

    @SerializedName("title")
    private String title;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("poster_path")
    private String image;

    @SerializedName("original_language")
    private String language;

    @SerializedName("original_title")
    private String original_title;

    @SerializedName("genre_ids")
    private int[] genre_ids;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("adult")
    private boolean isAdult;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String release_date;

    public int getVote_count() {
        return vote_count;
    }

    public int getId() {
        return id;
    }

    public String getVideo() {
        return video;
    }

    public String getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getImage() {
        return image;
    }

    public String getLanguage() {
        return language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

}
