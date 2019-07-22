package com.qourier.technicaltest.question2.pagging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.qourier.technicaltest.question2.api.RestApi;
import com.qourier.technicaltest.question2.api.RestApiFactory;
import com.qourier.technicaltest.question2.data.Movie;
import com.qourier.technicaltest.question2.data.MovieList;
import com.qourier.technicaltest.question2.state.NetworkState;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hoa Nguyen on Jul 21 2019.
 */
public class MovieDataSource extends PageKeyedDataSource<Long, Movie> {
    private static final String TAG = MovieDataSource.class.getSimpleName();
    private RestApi restApi;

    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<NetworkState> initialLoading;

    public MovieDataSource() {
        this.restApi = RestApiFactory.newInstance().getRestApi();

        networkState = new MutableLiveData<>();
        initialLoading = new MutableLiveData<>();
    }


    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull LoadInitialCallback<Long, Movie> callback) {

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        restApi.fetchMovies(RestApiFactory.API_KEY, 1)
                .enqueue(new Callback<MovieList>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                callback.onResult(response.body().getResults(), null, 2L);
                            }
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);

                        } else {
                            initialLoading.postValue(NetworkState.error(response.message()));
                            networkState.postValue(NetworkState.error(response.message()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(NetworkState.error(errorMessage));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params,
                           @NonNull LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params,
                          @NonNull LoadCallback<Long, Movie> callback) {

        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);

        restApi.fetchMovies(RestApiFactory.API_KEY, params.key)
                .enqueue(new Callback<MovieList>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Movie> result = null;
                            if (response.body() != null) {
                                result = response.body().getResults();
                            }
                            callback.onResult(result, params.key + 1);

                            networkState.postValue(NetworkState.LOADED);

                        } else {
                            networkState.postValue(NetworkState.error(response.message()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(NetworkState.error(errorMessage));
                    }
                });

    }
}
