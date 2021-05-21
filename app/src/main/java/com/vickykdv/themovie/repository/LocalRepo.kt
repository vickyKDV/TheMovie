package com.vickykdv.themovie.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vickykdv.themovie.database.DbRoom
import com.vickykdv.themovie.database.MovieEntity
import com.vickykdv.themovie.model.movie.DataMovie
import com.vickykdv.themovie.network.state.DetailState
import com.vickykdv.themovie.network.state.MovieState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LocalRepo @Inject constructor(
    private val database: DbRoom,
    private val config: PagedList.Config
) : Repository {

    var disposable: CompositeDisposable = CompositeDisposable()

    override fun getDetailData(movieId: Int, callback: MutableLiveData<DetailState>) {
        throw UnsupportedOperationException()
    }


    override fun getAllNowPlayingData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getAllUpcomingData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getAllTopRatedData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getAllPopularData(
        callback: MutableLiveData<MovieState>,
        data: MutableLiveData<PagedList<DataMovie>>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getFavoriteData(data: MutableLiveData<PagedList<MovieEntity>>) {
        LivePagedListBuilder(
            database.movie().getData(),
            config
        ).build().observeForever(data::postValue)
    }


    override fun addDataMovie(data: MovieEntity) {
        database.movie().add(data)
    }

    override fun checkDataMovie(data: MovieEntity): List<MovieEntity> {
        return database.movie().getDataById(data.id)
    }

    override fun deleteDataMovie(data: MovieEntity) {
        database.movie().delete(data)
    }




    override fun getDisposible(): CompositeDisposable = disposable

    override fun getDatabase(): DbRoom = database
}