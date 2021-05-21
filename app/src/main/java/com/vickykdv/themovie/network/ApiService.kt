package com.vickykdv.themovie.network

import com.vickykdv.themovie.model.detailmovie.ResponseDetailMovie
import com.vickykdv.themovie.model.movie.ResponseMovie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface ApiService {

    @GET("movie/{movieId}")
    fun getDetailMovie(
            @Path("movieId") movieId: Int
    ) : Single<ResponseDetailMovie>

    @GET("movie/{movieType}")
    fun getAllMovie(
        @Path("movieType") movieType: String,
        @Query("page") page : Int
    ) : Single<ResponseMovie>


}