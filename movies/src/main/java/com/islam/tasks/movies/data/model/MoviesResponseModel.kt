package com.islam.tasks.movies.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponseModel(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: List<MovieModel>? = null,
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)