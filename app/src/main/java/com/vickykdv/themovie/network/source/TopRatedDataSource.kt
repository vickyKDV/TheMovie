package com.vickykdv.themovie.network.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.vickykdv.themovie.base.BaseConstants.TOP_RATED
import com.vickykdv.themovie.model.movie.DataMovie
import com.vickykdv.themovie.network.ApiService
import com.vickykdv.themovie.network.state.MovieState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedDataSource @Inject constructor(private val apiService: ApiService) : PageKeyedDataSource<Int, DataMovie>() {

        lateinit var liveData: MutableLiveData<MovieState>

        private val disposable by lazy {
            CompositeDisposable()
        }

        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, DataMovie>
        ) {
            apiService.getAllMovie(TOP_RATED, 1)
                .map<MovieState>{
                    callback.onResult(it.data.toMutableList(), 1, 2)
                    MovieState.Result(it)
                }
                .onErrorReturn(MovieState::Error)
                .toFlowable()
                .startWith(MovieState.Loading)
                .subscribe(liveData::postValue)
                .let { return@let disposable::add }
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataMovie>) {
            apiService.getAllMovie(TOP_RATED, params.key)
                .map<MovieState>{
                    callback.onResult(it.data.toMutableList(), params.key + 1)
                    MovieState.Result(it)
                }
                .onErrorReturn(MovieState::Error)
                .toFlowable()
                .subscribe(liveData::postValue)
                .let { return@let disposable::add }
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataMovie>) {

        }
    }