package com.qourier.technicaltest.question2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qourier.technicaltest.R;
import com.qourier.technicaltest.question2.pagging.MovieListAdapter;
import com.qourier.technicaltest.question2.state.Status;
import com.qourier.technicaltest.question2.viewmodel.MovieListViewModel;

/**
 * Created by Hoa Nguyen on Jul 21 2019.
 */
public class QuestionTwoActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private View mLoading;
    private MovieListAdapter adapter;
    private MovieListViewModel movieListViewModel;

    public static void startActivity(Activity from) {
        Intent intent = new Intent(from, QuestionTwoActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.question_2_layout);

        mRecyclerView = findViewById(R.id.recyclerView);
        mLoading = findViewById(R.id.loading);

        movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        adapter = new MovieListAdapter(getApplicationContext());

        movieListViewModel.getMovieListLiveData().observe(this, pagedList -> {
            adapter.submitList(pagedList);
        });

        movieListViewModel.getNetworkState().observe(this, networkState -> {
            adapter.setNetworkState(networkState);
        });

        movieListViewModel.getRefreshState().observe(this, networkState -> {
            if (networkState.getStatus() == Status.RUNNING) {
                mRecyclerView.setVisibility(View.GONE);
                mLoading.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mLoading.setVisibility(View.GONE);
            }

        });

        mRecyclerView.setAdapter(adapter);
    }
}
