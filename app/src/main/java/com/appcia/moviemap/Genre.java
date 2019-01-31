package com.appcia.moviemap;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class Genre {

    @SerializedName("genres")
    private ArrayList<KeyVal> genreData;

    public ArrayList<KeyVal> getGenreData() {
        return genreData;
    }
}

class KeyVal{

    @SerializedName("id")
    private Integer key;
    @SerializedName("name")
    private String value;

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
