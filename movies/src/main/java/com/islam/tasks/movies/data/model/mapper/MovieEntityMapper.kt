package com.islam.tasks.movies.data.model.mapper

import com.islam.tasks.movies.data.model.MovieModel
import com.islam.tasks.movies.domain.entity.MovieEntity

fun MovieModel.toEntity(imagesBaseUrl: String) = MovieEntity(
    adult = adult ?: false,
    backdropPath = "$imagesBaseUrl$backdropPath",
    genreIds = genreIds ?: emptyList(),
    id = id ?: 0,
    originalLanguage = originalLanguage ?: "",
    originalTitle = originalTitle ?: "",
    overview = overview ?: "",
    popularity = popularity ?: 0.0,
    posterPath = "$imagesBaseUrl$posterPath",
    releaseDate = releaseDate ?: "",
    title = title ?: "",
    video = video ?: false,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0
)