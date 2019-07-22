package com.qourier.technicaltest.question2.pagging;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.qourier.technicaltest.databinding.MovieItemBinding;
import com.qourier.technicaltest.databinding.NetworkItemBinding;
import com.qourier.technicaltest.question2.data.Movie;
import com.qourier.technicaltest.question2.state.NetworkState;
import com.qourier.technicaltest.question2.state.Status;

/**
 * Created by Hoa Nguyen on Jul 22 2019.
 */
public class MovieListAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {

    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private NetworkState networkState;

    public MovieListAdapter(Context context) {
        super(Movie.DIFF_CALLBACK);
        this.context = context;
    }


    /*
     * Default method of RecyclerView.Adapter
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_PROGRESS) {
            NetworkItemBinding headerBinding = NetworkItemBinding.inflate(layoutInflater, parent, false);
            NetworkStateItemViewHolder viewHolder = new NetworkStateItemViewHolder(headerBinding);
            return viewHolder;

        } else {
            MovieItemBinding itemBinding = MovieItemBinding.inflate(layoutInflater, parent, false);
            MovieItemViewHolder viewHolder = new MovieItemViewHolder(itemBinding);
            return viewHolder;
        }
    }


    /*
     * Default method of RecyclerView.Adapter
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieItemViewHolder) {
            ((MovieItemViewHolder) holder).bindTo(getItem(position));
        } else {
            ((NetworkStateItemViewHolder) holder).bindView(networkState);
        }
    }


    /*
     * Default method of RecyclerView.Adapter
     */
    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }


    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


    /*
     * We define A custom ViewHolder for the list item
     */
    public class MovieItemViewHolder extends RecyclerView.ViewHolder {

        private MovieItemBinding binding;

        public MovieItemViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Movie movie) {
            Log.d("Binding", movie.toString());
            binding.setMovie(movie);
        }
    }


    /*
     * We define A custom ViewHolder for the progressView
     */
    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private NetworkItemBinding binding;

        public NetworkStateItemViewHolder(NetworkItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == Status.RUNNING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == Status.FAILED) {
                binding.errorMsg.setVisibility(View.VISIBLE);
                binding.errorMsg.setText(networkState.getMessage());
            } else {
                binding.errorMsg.setVisibility(View.GONE);
            }
        }
    }
}