package com.islam.tasks.details.presentation.model

sealed class MovieDetailsIntent {
    data class GetDetails(val id: Int) : MovieDetailsIntent()
}