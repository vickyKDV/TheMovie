package com.vickykdv.themovie.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vickykdv.themovie.database.DbRoom
import com.vickykdv.themovie.database.MovieEntity
import com.vickykdv.themovie.model.movie.DataMovie
import com.vickykdv.themovie.network.ApiService
import com.vickykdv.themovie.network.factory.Factory
import com.vickykdv.themovie.network.state.DetailState
import com.vickykdv.themovie.network.state.MovieState
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RemoteRepo @Inject constructor(
    private val apiService: ApiService,
    private val config : PagedList.Config,
    private val factory: Factory
) : Repository {

    var disposable: CompositeDisposable = CompositeDisposable()

    override fun getDisposible(): CompositeDisposable = disposable



//

    override fun getDetailData(movieId: Int, callback: MutableLiveData<DetailState>) {
        apiService.getDetailMovie(movieId)
            .map<DetailState>(DetailState::Result)
            .onErrorReturn(DetailState::Error)
            .toFlowable()
            .startWith(DetailState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getAllNowPlayingData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            LivePagedListBuilder(
                factory.nowPlayingMovieDataFactory.also {
                    it.liveData = callback
                },
                config
            ).build().observeForever(data::postValue)
        }
    }

    override fun getAllUpcomingData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            LivePagedListBuilder(
                factory.upcomingMovieDataFactory.also {
                    it.liveData = callback
                },
                config
            ).build().observeForever(data::postValue)
        }
    }

    override fun getAllTopRatedData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            LivePagedListBuilder(
                factory.topRatedMovieDataFactory.also {
                    it.liveData = callback
                },
                config
            ).build().observeForever(data::postValue)
        }
    }

    override fun getAllPopularData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            LivePagedListBuilder(
                factory.popularDataFactory.also {
                    it.liveData = callback
                },
                config
            ).build().observeForever(data::postValue)
        }
    }

    override fun getFavoriteData(data: MutableLiveData<PagedList<MovieEntity>>) {
        throw UnsupportedOperationException()
    }


    override fun addDataMovie(data: MovieEntity) {
        throw UnsupportedOperationException()
    }

    override fun checkDataMovie(data: MovieEntity): List<MovieEntity> {
        throw UnsupportedOperationException()
    }

    override fun deleteDataMovie(data: MovieEntity) {
        throw UnsupportedOperationException()
    }



    override fun getDatabase(): DbRoom {
        throw UnsupportedOperationException()
    }
}