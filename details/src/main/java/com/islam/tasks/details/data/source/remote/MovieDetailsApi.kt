package com.islam.tasks.details.data.source.remote

import com.islam.tasks.details.data.model.MovieDetailsResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailsApi {
    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int):MovieDetailsResponseModel
}