package com.rh.mvvm_jsonparsing_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.rh.mvvm_jsonparsing_java.apiUtils.NetworkState;
import com.rh.mvvm_jsonparsing_java.model.Movie;
import com.rh.mvvm_jsonparsing_java.view.MovieInTheaterAdapter;
import com.rh.mvvm_jsonparsing_java.viewModels.MoviesInTheaterViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MoviesInTheaterViewModel mMoviesViewModel;
    private RecyclerView rvMovieList;
    private MovieInTheaterAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, "on create:");
        rvMovieList = findViewById(R.id.rvMovieList);
        adapter = new MovieInTheaterAdapter(this);
        mMoviesViewModel = ViewModelProviders.of(this).get(MoviesInTheaterViewModel.class);

        mMoviesViewModel.getMoviesIntheaterList().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                Log.e(TAG, "onChanged:"+movies.size());
                adapter.submitList(movies);
            }
        });

        mMoviesViewModel.getNetworkStateLiveData().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                Log.e(TAG, "onChanged: Network state changed");
                adapter.setNetworkState(networkState);
            }
        });

        rvMovieList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMovieList.setAdapter(adapter);
    }
}