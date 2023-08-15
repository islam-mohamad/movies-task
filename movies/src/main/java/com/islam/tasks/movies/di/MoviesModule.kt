package com.islam.tasks.movies.di

import com.islam.tasks.movies.data.repository.MoviesRepositoryImpl
import com.islam.tasks.movies.data.source.remote.MoviesApi
import com.islam.tasks.movies.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
abstract class MoviesModule {
    @Binds
    abstract fun bindMoviesRepository(imp: MoviesRepositoryImpl): MoviesRepository

    companion object {
        @Provides
        fun provideMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create()
    }
}