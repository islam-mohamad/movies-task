package com.islam.tasks.movies.data.source.remote

import com.islam.tasks.movies.data.model.MoviesResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("discover/movie")
    suspend fun getMovies(): MoviesResponseModel
}