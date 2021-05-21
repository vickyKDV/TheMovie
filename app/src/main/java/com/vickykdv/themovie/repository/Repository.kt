package com.vickykdv.themovie.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.vickykdv.themovie.database.DbRoom
import com.vickykdv.themovie.database.MovieEntity
import com.vickykdv.themovie.model.movie.DataMovie
import com.vickykdv.themovie.network.state.DetailState
import com.vickykdv.themovie.network.state.MovieState
import io.reactivex.disposables.CompositeDisposable

interface Repository {

    fun getDetailData(movieId: Int, callback : MutableLiveData<DetailState>)

    fun getAllNowPlayingData(
        callback : MutableLiveData<MovieState>,
        data : MutableLiveData<PagedList<DataMovie>>
    )

    fun getAllUpcomingData(
        callback : MutableLiveData<MovieState>,
        data : MutableLiveData<PagedList<DataMovie>>
    )
    fun getAllTopRatedData(
        callback : MutableLiveData<MovieState>,
        data : MutableLiveData<PagedList<DataMovie>>
    )
    fun getAllPopularData(
        callback : MutableLiveData<MovieState>,
        data : MutableLiveData<PagedList<DataMovie>>
    )


    fun getFavoriteData(data : MutableLiveData<PagedList<MovieEntity>>)
    fun addDataMovie(data : MovieEntity)
    fun checkDataMovie(data: MovieEntity) : List<MovieEntity>
    fun deleteDataMovie(data : MovieEntity)


    fun getDisposible() : CompositeDisposable
    fun getDatabase() : DbRoom
}