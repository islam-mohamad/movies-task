package com.islam.tasks.yassir.di

import com.islam.tasks.movies.presentation.navigation.MoviesNavigation
import com.islam.tasks.yassir.navigation.MoviesNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun bindMoviesNavigation(impl: MoviesNavigationImpl): MoviesNavigation
}