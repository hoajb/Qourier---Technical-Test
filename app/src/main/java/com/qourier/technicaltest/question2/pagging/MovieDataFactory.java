package com.qourier.technicaltest.question2.pagging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * Created by Hoa Nguyen on Jul 22 2019.
 */
public class MovieDataFactory extends DataSource.Factory {

    private MutableLiveData<MovieDataSource> mutableLiveData;
    private MovieDataSource  movieDataSource;

    public MovieDataFactory() {
        this.mutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        movieDataSource = new MovieDataSource();
        mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
