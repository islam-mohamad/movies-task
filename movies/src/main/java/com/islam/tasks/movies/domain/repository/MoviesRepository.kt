package com.islam.tasks.movies.domain.repository

import com.islam.tasks.movies.domain.entity.MovieEntity

interface MoviesRepository {
    suspend fun getMovies(): List<MovieEntity>
}