package com.islam.tasks.movies.domain.usecase

import com.islam.tasks.movies.domain.entity.MovieEntity
import com.islam.tasks.movies.domain.entity.exception.EmptyMoviesException
import com.islam.tasks.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {
    suspend operator fun invoke(): List<MovieEntity> {
        return try {
            repository.getMovies()
        } catch (error: EmptyMoviesException) {
            emptyList()
        }
    }
}