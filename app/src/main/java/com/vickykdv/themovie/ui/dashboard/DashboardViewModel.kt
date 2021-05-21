package com.vickykdv.themovie.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.vickykdv.themovie.model.movie.DataMovie
import com.vickykdv.themovie.network.state.MovieState
import com.vickykdv.themovie.repository.Repository

class DashboardViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {


    val state : MutableLiveData<MovieState> by lazy {
        MutableLiveData<MovieState>()
    }

    val data : MutableLiveData<PagedList<DataMovie>> by lazy {
        MutableLiveData<PagedList<DataMovie>>()
    }

    fun getUpcomingData() {
        repository.getAllUpcomingData(state,data)
    }

    fun getPopularData() {
        repository.getAllPopularData(state,data)
    }

    fun getTopRatedData() {
        repository.getAllTopRatedData(state,data)
    }

    fun getNowPlaying() {
        repository.getAllNowPlayingData(state,data)
    }
}