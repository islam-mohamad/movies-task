package com.islam.tasks.movies.presentation.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.tasks.core.R
import com.islam.tasks.core.di.MainDispatcher
import com.islam.tasks.core.network.NoInternetException
import com.islam.tasks.core.network.ServerError
import com.islam.tasks.movies.domain.usecase.GetMoviesUseCase
import com.islam.tasks.movies.presentation.navigation.MoviesNavigation
import com.islam.tasks.movies.presentation.uimodel.MovieUiModel
import com.islam.tasks.movies.presentation.uimodel.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _moviesStateFlow: MutableStateFlow<List<MovieUiModel>> =
        MutableStateFlow(emptyList())
    val moviesStateFlow = _moviesStateFlow.asStateFlow()

    private val _loadingChannel: Channel<Boolean> = Channel()
    val loadingChannel = _loadingChannel.receiveAsFlow()

    private val _errorMsgChannel: Channel<String> = Channel()
    val errorMsgChannel = _errorMsgChannel.receiveAsFlow()

    private val _errorResChannel: Channel<Int> = Channel()
    val errorResChannel = _errorResChannel.receiveAsFlow()

    fun getMovies() {
        viewModelScope.launch(dispatcher) {
            setLoading(true)
            try {
                val movies = getMoviesUseCase()
                _moviesStateFlow.value = movies.map { it.toUiModel() }
                setLoading(false)
            } catch (t: Throwable) {
                setLoading(false)
                mapError(t)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) =
        viewModelScope.launch(dispatcher) { _loadingChannel.send(isLoading) }

    private fun mapError(t: Throwable) {
        viewModelScope.launch {
            when (t) {
                is NoInternetException -> _errorResChannel.send(R.string.internet_error)
                is ServerError -> _errorMsgChannel.send(t.errorMessage)
                else -> _errorResChannel.send(R.string.general_error)
            }
        }
    }
}