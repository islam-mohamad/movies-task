package com.islam.tasks.details.di

import com.islam.tasks.details.data.repository.MovieDetailsRepositoryImpl
import com.islam.tasks.details.data.source.remote.MovieDetailsApi
import com.islam.tasks.details.domain.repository.MovieDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieDetailsModule {

    @Binds
    abstract fun bindMovieDetailsRepository(imp: MovieDetailsRepositoryImpl): MovieDetailsRepository

    companion object {
        @Provides
        fun provideMovieDetailsApi(retrofit: Retrofit): MovieDetailsApi = retrofit.create()
    }
}