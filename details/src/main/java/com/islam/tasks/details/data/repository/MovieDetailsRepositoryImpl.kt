package com.islam.tasks.details.data.repository

import com.islam.tasks.core.di.IODispatcher
import com.islam.tasks.core.di.ImagesBaseUrl
import com.islam.tasks.core.network.mapNetworkErrors
import com.islam.tasks.details.data.model.mapper.toEntity
import com.islam.tasks.details.data.source.remote.MovieDetailsApi
import com.islam.tasks.details.domain.entity.MovieDetailsEntity
import com.islam.tasks.details.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val api: MovieDetailsApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    @ImagesBaseUrl private val imagesBaseUrl: String
):MovieDetailsRepository {
    override suspend fun getMovieDetails(id: Int): MovieDetailsEntity {
        return withContext(dispatcher) {
            try {
                api.getMovieDetails(id).toEntity(imagesBaseUrl)
            } catch (t: Throwable) {
                Timber.e(t)
                throw t.mapNetworkErrors()
            }
        }
    }
}