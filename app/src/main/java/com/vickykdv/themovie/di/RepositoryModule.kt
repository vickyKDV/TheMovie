package com.vickykdv.themovie.di

import androidx.paging.PagedList
import com.vickykdv.themovie.database.DbRoom
import com.vickykdv.themovie.network.ApiService
import com.vickykdv.themovie.network.factory.Factory
import com.vickykdv.themovie.repository.DataRepository
import com.vickykdv.themovie.repository.LocalRepo
import com.vickykdv.themovie.repository.RemoteRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRemoteRepository(
        apiService: ApiService,
        config : PagedList.Config,
        factory : Factory
    ) : RemoteRepo = RemoteRepo(
        apiService,
        config,
        factory
    )

    @Singleton
    @Provides
    fun provideLocalRepository(
        database : DbRoom,
        config : PagedList.Config
    ) : LocalRepo = LocalRepo(database, config)


    @Singleton
    @Provides
    fun provideDataRepository(
        remoteRepository: RemoteRepo,
        localRepository: LocalRepo
    ) : DataRepository = DataRepository(
        remoteRepository,
        localRepository
    )
}