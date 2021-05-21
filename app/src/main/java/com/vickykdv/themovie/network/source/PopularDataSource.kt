package com.vickykdv.themovie.network.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.vickykdv.themovie.base.BaseConstants.POPULAR
import com.vickykdv.themovie.model.movie.DataMovie
import com.vickykdv.themovie.network.ApiService
import com.vickykdv.themovie.network.state.MovieState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularDataSource @Inject constructor(private val apiService: ApiService) : PageKeyedDataSource<Int, DataMovie>() {

        lateinit var liveData: MutableLiveData<MovieState>

        private val disposable by lazy {
            CompositeDisposable()
        }

        override fun loadInitial(
            params: PageKeyedDataSource.LoadInitialParams<Int>,
            callback: PageKeyedDataSource.LoadInitialCallback<Int, DataMovie>
        ) {
            apiService.getAllMovie(POPULAR, 1)
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

        override fun loadAfter(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, DataMovie>) {
            apiService.getAllMovie(POPULAR, params.key)
                .map<MovieState>{
                    callback.onResult(it.data.toMutableList(), params.key + 1)
                    MovieState.Result(it)
                }
                .onErrorReturn(MovieState::Error)
                .toFlowable()
                .subscribe(liveData::postValue)
                .let { return@let disposable::add }
        }

        override fun loadBefore(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, DataMovie>) {

        }
    }