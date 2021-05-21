package com.vickykdv.themovie.network.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.vickykdv.themovie.model.movie.DataMovie
import com.vickykdv.themovie.network.source.TopRatedDataSource
import com.vickykdv.themovie.network.state.MovieState
import javax.inject.Inject

class TopRatedDataFactory @Inject constructor(private val dataSource: TopRatedDataSource): DataSource.Factory<Int, DataMovie>(){

    lateinit var liveData: MutableLiveData<MovieState>

    override fun create(): DataSource<Int, DataMovie> {
        return dataSource.also {
            it.liveData = liveData
        }
    }
}