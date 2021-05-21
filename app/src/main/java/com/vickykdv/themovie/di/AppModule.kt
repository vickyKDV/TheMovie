package com.vickykdv.themovie.di

import android.content.Context
import androidx.paging.PagedList
import com.vickykdv.themovie.database.DbRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideConfig() : PagedList.Config = PagedList.Config.Builder()
        .setPageSize(1)
        .setInitialLoadSizeHint(2)
        .setPrefetchDistance(1)
        .setEnablePlaceholders(false)
        .build()

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ) : DbRoom = DbRoom(context)


}