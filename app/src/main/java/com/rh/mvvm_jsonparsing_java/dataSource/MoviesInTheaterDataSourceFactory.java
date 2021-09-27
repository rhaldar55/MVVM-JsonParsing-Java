package com.rh.mvvm_jsonparsing_java.dataSource;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.rh.mvvm_jsonparsing_java.dataModels.TMDBWebservices;
import com.rh.mvvm_jsonparsing_java.model.Movie;

import java.util.concurrent.Executor;

public class MoviesInTheaterDataSourceFactory extends DataSource.Factory {

    private static final String TAG = "MoviesInTheaterData";
    MoviesInTheaterDataSource moviesDataSource;
    MutableLiveData<MoviesInTheaterDataSource> mutableLiveData;

    Executor executor;
    TMDBWebservices webservices;

    public MoviesInTheaterDataSourceFactory(Executor executor, TMDBWebservices webservices) {
        this.executor = executor;
        this.webservices = webservices;
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<MoviesInTheaterDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

    @Override
    public DataSource create() {

        Log.e(TAG, "Create : ");

        moviesDataSource = new MoviesInTheaterDataSource(webservices, executor);
        mutableLiveData.postValue(moviesDataSource);
        return moviesDataSource;

    }
}
