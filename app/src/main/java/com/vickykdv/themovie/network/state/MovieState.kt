package com.vickykdv.themovie.network.state

import com.vickykdv.themovie.model.movie.ResponseMovie

sealed class MovieState {
    object Loading : MovieState()
    data class Result(val data : ResponseMovie) : MovieState()
    data class Error(val error : Throwable) : MovieState()
}