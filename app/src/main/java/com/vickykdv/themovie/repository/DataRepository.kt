package com.vickykdv.themovie.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.vickykdv.themovie.database.DbRoom
import com.vickykdv.themovie.database.MovieEntity
import com.vickykdv.themovie.model.movie.DataMovie
import com.vickykdv.themovie.network.state.DetailState
import com.vickykdv.themovie.network.state.MovieState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val remoteRepo: RemoteRepo,
    private val localRepo: LocalRepo
) :Repository{
    override fun getDetailData(movieId: Int, callback: MutableLiveData<DetailState>) {
        remoteRepo.getDetailData(movieId, callback)
    }

    override fun getAllNowPlayingData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        remoteRepo.getAllNowPlayingData(callback, data)
    }

    override fun getAllUpcomingData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        remoteRepo.getAllUpcomingData(callback, data)
    }

    override fun getAllTopRatedData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        remoteRepo.getAllTopRatedData(callback, data)
    }

    override fun getAllPopularData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        remoteRepo.getAllPopularData(callback, data)
    }

    override fun getFavoriteData(data: MutableLiveData<PagedList<MovieEntity>>) {
        localRepo.getFavoriteData(data)
    }

    override fun addDataMovie(data: MovieEntity) {
        localRepo.addDataMovie(data)
    }

    override fun checkDataMovie(data: MovieEntity): List<MovieEntity> = localRepo.checkDataMovie(data)

    override fun deleteDataMovie(data: MovieEntity) = localRepo.deleteDataMovie(data)

    override fun getDisposible(): CompositeDisposable = remoteRepo.getDisposible()

    override fun getDatabase(): DbRoom = localRepo.getDatabase()

}