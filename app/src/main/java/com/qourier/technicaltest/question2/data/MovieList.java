package com.qourier.technicaltest.question2.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hoa Nguyen on Jul 21 2019.
 */
public class MovieList {
    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<Movie> results;

    public int getPage() {
        return page;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }
}
