package com.islam.tasks.details.data.model.mapper

import com.islam.tasks.details.data.model.MovieDetailsResponseModel
import com.islam.tasks.details.domain.entity.MovieDetailsEntity

fun MovieDetailsResponseModel.toEntity(imagesBaseUrl: String) = MovieDetailsEntity(
    adult = adult ?: false,
    backdropPath = "$imagesBaseUrl$backdropPath",
    budget = budget ?: 0,
    homepage = homepage ?: "",
    id = id ?: 0,
    imdbId = imdbId ?: "",
    originalLanguage = originalLanguage ?: "",
    originalTitle = originalTitle ?: "",
    overview = overview ?: "",
    popularity = popularity ?: 0.0,
    posterPath = "$imagesBaseUrl$posterPath",
    releaseDate = releaseDate ?: "",
    revenue = revenue ?: 0,
    runtime = runtime ?: 0,
    status = status ?: "",
    tagline = tagline ?: "",
    title = title ?: "",
    video = video ?: false,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0
)