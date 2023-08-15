package com.islam.tasks.movies.data.repository

import com.islam.tasks.core.di.IODispatcher
import com.islam.tasks.core.di.ImagesBaseUrl
import com.islam.tasks.core.network.mapNetworkErrors
import com.islam.tasks.movies.data.model.mapper.toEntity
import com.islam.tasks.movies.data.source.remote.MoviesApi
import com.islam.tasks.movies.domain.entity.MovieEntity
import com.islam.tasks.movies.domain.entity.exception.EmptyMoviesException
import com.islam.tasks.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    @ImagesBaseUrl private val imagesBaseUrl: String
) : MoviesRepository {
    override suspend fun getMovies(): List<MovieEntity> {
        return withContext(dispatcher) {
            try {
                api.getMovies().results?.map { it.toEntity(imagesBaseUrl) }
                    ?: throw EmptyMoviesException()
            } catch (t: Throwable) {
                Timber.e(t)
                throw t.mapNetworkErrors()
            }
        }
    }

}