package com.islam.tasks.details.domain

import com.islam.tasks.details.domain.entity.MovieDetailsEntity
import com.islam.tasks.details.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: MovieDetailsRepository) {
    suspend operator fun invoke(id: Int): MovieDetailsEntity {
        return repository.getMovieDetails(id)
    }
}