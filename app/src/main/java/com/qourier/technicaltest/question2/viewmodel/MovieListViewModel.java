package com.qourier.technicaltest.question2.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.qourier.technicaltest.question2.data.Movie;
import com.qourier.technicaltest.question2.pagging.MovieDataFactory;
import com.qourier.technicaltest.question2.pagging.MovieDataSource;
import com.qourier.technicaltest.question2.state.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Hoa Nguyen on Jul 22 2019.
 */
public class MovieListViewModel extends ViewModel {
    private Executor executor;
    private LiveData<NetworkState> networkState;
    private LiveData<NetworkState> refreshState;
    private LiveData<PagedList<Movie>> movieListLiveData;

    public MovieListViewModel() {
        init();
    }

    private void init() {
        executor = Executors.newFixedThreadPool(5);

        MovieDataFactory MovieDataFactory = new MovieDataFactory();
        networkState = Transformations.switchMap(MovieDataFactory.getMutableLiveData(),
                (Function<MovieDataSource, LiveData<NetworkState>>) MovieDataSource::getNetworkState);

        refreshState = Transformations.switchMap(MovieDataFactory.getMutableLiveData(),
                (Function<MovieDataSource, LiveData<NetworkState>>) MovieDataSource::getInitialLoading);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        movieListLiveData = (new LivePagedListBuilder(MovieDataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<NetworkState> getRefreshState() {
        return refreshState;
    }

    public LiveData<PagedList<Movie>> getMovieListLiveData() {
        return movieListLiveData;
    }

}
