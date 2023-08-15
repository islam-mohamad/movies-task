package com.islam.tasks.details.domain.repository

import com.islam.tasks.details.domain.entity.MovieDetailsEntity

interface MovieDetailsRepository {
    suspend fun getMovieDetails(id: Int): MovieDetailsEntity
}