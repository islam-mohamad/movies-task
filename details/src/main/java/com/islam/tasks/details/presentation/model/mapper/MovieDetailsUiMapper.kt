package com.islam.tasks.details.presentation.model.mapper

import com.islam.tasks.details.domain.entity.MovieDetailsEntity
import com.islam.tasks.details.presentation.model.MovieDetailsUiModel

fun MovieDetailsEntity.toUiModel() = MovieDetailsUiModel(
    title = title, posterPath = posterPath, releaseDate = releaseDate, overview = overview
)