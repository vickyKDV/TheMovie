package com.vickykdv.themovie.network.factory

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class Factory @Inject constructor (
    val nowPlayingMovieDataFactory: NowPlayingDataFactory,
    val upcomingMovieDataFactory: UpComingDataFactory,
    val topRatedMovieDataFactory: TopRatedDataFactory,
    val popularDataFactory: PopularDataFactory,
)