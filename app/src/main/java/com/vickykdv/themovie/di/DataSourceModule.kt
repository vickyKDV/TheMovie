package com.vickykdv.themovie.di

import com.vickykdv.themovie.network.ApiService
import com.vickykdv.themovie.network.source.PopularDataSource
import com.vickykdv.themovie.network.source.TopRatedDataSource
import com.vickykdv.themovie.network.source.UpComingDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideUpcomingMovieDataSource(
        apiservice: ApiService
    ) : UpComingDataSource = UpComingDataSource(apiservice)

    @Provides
    @Singleton
    fun provideTopRatedDataSource(
        apiservice: ApiService
    ) : TopRatedDataSource = TopRatedDataSource(apiservice)

    @Provides
    @Singleton
    fun providePopularMovieDataSource(
        apiservice: ApiService
    ) : PopularDataSource = PopularDataSource(apiservice)


}