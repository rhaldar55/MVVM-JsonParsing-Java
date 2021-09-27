package com.rh.mvvm_jsonparsing_java.viewModels;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.rh.mvvm_jsonparsing_java.apiUtils.NetworkState;
import com.rh.mvvm_jsonparsing_java.apiUtils.ServiceGenerator;
import com.rh.mvvm_jsonparsing_java.dataModels.TMDBWebservices;
import com.rh.mvvm_jsonparsing_java.dataSource.MoviesInTheaterDataSource;
import com.rh.mvvm_jsonparsing_java.dataSource.MoviesInTheaterDataSourceFactory;
import com.rh.mvvm_jsonparsing_java.model.Movie;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MoviesInTheaterViewModel extends ViewModel {

    private static final String TAG = "theaterViewModel";
    private LiveData<PagedList<Movie>> moviesInTheaterList;
    private LiveData<NetworkState> networkStateLiveData;
    private Executor executor;
    private LiveData<MoviesInTheaterDataSource> dataSource;

    public MoviesInTheaterViewModel() {
        Log.e(TAG, "MoviesInTheaterViewModel: ");
        executor = Executors.newFixedThreadPool(5);
        TMDBWebservices webservices = ServiceGenerator.createService(TMDBWebservices.class);
        MoviesInTheaterDataSourceFactory factory = new MoviesInTheaterDataSourceFactory(executor,webservices);

        dataSource = factory.getMutableLiveData();
        networkStateLiveData = Transformations.switchMap(
                factory.getMutableLiveData(),
                new Function<MoviesInTheaterDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(MoviesInTheaterDataSource input) {
                        Log.e(TAG, "Apply neteork change :");

                        return input.getNetworkState();
                    }
                }
        );

        PagedList.Config pageConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20).build();


        moviesInTheaterList = new LivePagedListBuilder<Long, Movie>(factory, pageConfig)
                .build();

    }

    public LiveData<PagedList<Movie>> getMoviesIntheaterList() {
        Log.e("TAG", "getMoviesInTheaterList:"+ moviesInTheaterList.toString());
        return moviesInTheaterList;
    }

    public LiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }


}
