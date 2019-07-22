package com.qourier.technicaltest.question2.api;

/**
 * Created by Hoa Nguyen on Jul 21 2019.
 */

import com.qourier.technicaltest.question2.data.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {
    @GET("discover/movie")
    Call<MovieList> fetchMovies(
            @Query("api_key") String apiKey,
            @Query("page") long page);
}
