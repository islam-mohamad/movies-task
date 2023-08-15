package com.islam.tasks.movies.presentation.uimodel.mapper

import com.islam.tasks.movies.domain.entity.MovieEntity
import com.islam.tasks.movies.presentation.uimodel.MovieUiModel

fun MovieEntity.toUiModel() =
    MovieUiModel(id = id, title = title, posterPath = posterPath, releaseDate = releaseDate)