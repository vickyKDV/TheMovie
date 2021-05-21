package com.vickykdv.themovie.ui.favourite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.vickykdv.themovie.database.MovieEntity
import com.vickykdv.themovie.repository.Repository

class FavouriteViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val data : MutableLiveData<PagedList<MovieEntity>> by lazy {
        MutableLiveData<PagedList<MovieEntity>>()
    }

    fun getFavoriteMovie(){
        repository.getFavoriteData(data)
    }
}