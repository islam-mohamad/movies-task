package com.islam.tasks.details.presentation.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.tasks.core.R
import com.islam.tasks.core.di.MainDispatcher
import com.islam.tasks.core.network.NoInternetException
import com.islam.tasks.core.network.ServerError
import com.islam.tasks.details.domain.GetMovieDetailsUseCase
import com.islam.tasks.details.presentation.model.MovieDetailsIntent
import com.islam.tasks.details.presentation.model.MovieDetailsState
import com.islam.tasks.details.presentation.model.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState> = _state

    private val _intent: MutableSharedFlow<MovieDetailsIntent> = MutableSharedFlow()
    private val intent = _intent.asSharedFlow()


    init {
        viewModelScope.launch {
            intent.collect {
                processIntent(it)
            }
        }
    }

    fun sendIntent(intent: MovieDetailsIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun processIntent(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.GetDetails -> loadMovieDetails(intent.id)
        }
    }

    private fun loadMovieDetails(id: Int) {
        viewModelScope.launch(dispatcher) {
            try {
                _state.value = _state.value.copy(isLoading = true, errorRes = null, errorMsg = null)
                val movie = getMovieDetailsUseCase(id)
                _state.value =
                    _state.value.copy(isLoading = false, movieDetails = movie.toUiModel())
            } catch (t: Throwable) {
                mapError(t)
            }
        }
    }

    private fun mapError(t: Throwable) {
        viewModelScope.launch {
            _state.value = when (t) {
                is NoInternetException -> _state.value.copy(
                    isLoading = false, errorRes = R.string.internet_error
                )

                is ServerError -> _state.value.copy(isLoading = false, errorMsg = t.errorMessage)
                else -> _state.value.copy(isLoading = false, errorRes = R.string.general_error)
            }
        }
    }
}