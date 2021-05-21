package com.vickykdv.themovie.network.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.vickykdv.themovie.model.movie.DataMovie
import com.vickykdv.themovie.network.source.NowPlayingDataSource
import com.vickykdv.themovie.network.state.MovieState
import javax.inject.Inject

class NowPlayingDataFactory @Inject constructor(private val dataSource: NowPlayingDataSource): DataSource.Factory<Int, DataMovie>(){

    lateinit var liveData: MutableLiveData<MovieState>

    override fun create(): DataSource<Int, DataMovie> {
        return dataSource.also {
            it.liveData = liveData
        }
    }
}