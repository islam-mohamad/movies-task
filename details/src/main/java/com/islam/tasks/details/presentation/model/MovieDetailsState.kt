package com.islam.tasks.details.presentation.model

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetailsUiModel? = null,
    val errorMsg: String? = null,
    val errorRes: Int? = null
)