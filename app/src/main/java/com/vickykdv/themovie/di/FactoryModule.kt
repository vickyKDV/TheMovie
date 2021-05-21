package com.vickykdv.themovie.di

import com.vickykdv.themovie.network.factory.*
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
class FactoryModule {

    @Provides
    @Singleton
    fun provideFactory(
        nowPlayingDataFactory: NowPlayingDataFactory,
        upcomingDataFactory: UpComingDataFactory,
        topRatedDataFactory: TopRatedDataFactory,
        popularDataFactory: PopularDataFactory
    ) : Factory = Factory(
        nowPlayingDataFactory,
        upcomingDataFactory,
        topRatedDataFactory,
        popularDataFactory,
    )

    @Provides
    @Singleton
    fun provideUpcomingFactory(
        upcomingDataSource: UpComingDataSource
    ) : UpComingDataFactory = UpComingDataFactory(upcomingDataSource)

    @Provides
    @Singleton
    fun provideTopRatedFactory(
        topRatedDataSource: TopRatedDataSource
    ) : TopRatedDataFactory = TopRatedDataFactory(topRatedDataSource)

    @Provides
    @Singleton
    fun providePopularFactory(
        popularDataSource: PopularDataSource
    ) : PopularDataFactory = PopularDataFactory(popularDataSource)


}