package com.vickykdv.themovie.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vickykdv.themovie.database.MovieEntity
import com.vickykdv.themovie.network.state.DetailState
import com.vickykdv.themovie.repository.Repository
import kotlinx.coroutines.*

class DetailViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {


    val state : MutableLiveData<DetailState> by lazy {
        MutableLiveData<DetailState>()
    }

    val stateFavorite : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getDetailMovie(movieId: Int) {
        repository.getDetailData(movieId, state)
    }

    fun addToFavorite(data : MovieEntity){
        CoroutineScope(Dispatchers.IO).launch {
            val checkData = async { repository.checkDataMovie(data) }
            if(checkData.await().isEmpty()){
                repository.addDataMovie(data)
                withContext(Dispatchers.Main){
                    stateFavorite.postValue(true)
                }
            }else{
                repository.deleteDataMovie(data)
                withContext(Dispatchers.Main){
                    stateFavorite.postValue(false)
                }
            }
        }
    }

    fun checkFavorite(data : MovieEntity){
        CoroutineScope(Dispatchers.IO).launch {
            val checkData = async { repository.checkDataMovie(data) }
            if(checkData.await().isNotEmpty()){
                withContext(Dispatchers.Main){
                    stateFavorite.postValue(true)
                }
            }else{
                withContext(Dispatchers.Main){
                    stateFavorite.postValue(false)
                }
            }
        }
    }
}