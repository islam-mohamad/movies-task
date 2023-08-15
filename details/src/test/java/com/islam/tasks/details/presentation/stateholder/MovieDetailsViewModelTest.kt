package com.islam.tasks.details.presentation.stateholder

import com.islam.tasks.core.R
import com.islam.tasks.core.test.test
import com.islam.tasks.details.domain.GetMovieDetailsUseCase
import com.islam.tasks.details.domain.entity.MovieDetailsEntity
import com.islam.tasks.details.presentation.model.MovieDetailsIntent
import com.islam.tasks.details.presentation.model.MovieDetailsState
import com.islam.tasks.details.presentation.model.MovieDetailsUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {
    private lateinit var viewModel: MovieDetailsViewModel

    @Mock
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private val testScheduler = TestCoroutineScheduler()
    private lateinit var dispatcher: TestDispatcher

    @Mock
    private lateinit var movieDetailsEntity: MovieDetailsEntity

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        viewModel = MovieDetailsViewModel(
            dispatcher = dispatcher, getMovieDetailsUseCase = getMovieDetailsUseCase
        )
    }

    @Test
    fun `given GetDetails intent,  getMovieDetailsUseCase should be invoked and return success`() =
        runTest {
            //Arrange
            whenever(getMovieDetailsUseCase.invoke(Mockito.anyInt())).thenReturn(movieDetailsEntity)
            whenever(movieDetailsEntity.title).thenReturn(TITLE)
            whenever(movieDetailsEntity.releaseDate).thenReturn(RELEASE_DATE)
            whenever(movieDetailsEntity.overview).thenReturn(OVERVIEW)
            whenever(movieDetailsEntity.posterPath).thenReturn(POSTER_PATH)
            val observer = viewModel.state.test(this)

            //Action
            viewModel.sendIntent(MovieDetailsIntent.GetDetails(MOVIE_ID))
            advanceUntilIdle()

            //Assert
            observer.assertValuesAndFinish(
                MovieDetailsState(), MovieDetailsState(isLoading = true), MovieDetailsState(
                    movieDetails = MovieDetailsUiModel(
                        title = TITLE,
                        posterPath = POSTER_PATH,
                        releaseDate = RELEASE_DATE,
                        overview = OVERVIEW
                    )
                )
            )
        }

    @Test
    fun `given GetDetails intent,  and if getMovieDetailsUseCase throws an exception it should return Error`() =
        runTest {
            //Arrange
            Mockito.`when`(getMovieDetailsUseCase.invoke(Mockito.anyInt()))
                .thenThrow(RuntimeException())

            val observer = viewModel.state.test(this)

            //Action
            viewModel.sendIntent(MovieDetailsIntent.GetDetails(MOVIE_ID))
            advanceUntilIdle()

            //Assert
            observer.assertValuesAndFinish(
                MovieDetailsState(), MovieDetailsState(isLoading = true), MovieDetailsState(errorRes = R.string.general_error)
            )
        }

    companion object {
        private const val MOVIE_ID = 0
        private const val TITLE = "TITLE"
        private const val POSTER_PATH = "POSTER_PATH"
        private const val RELEASE_DATE = "RELEASE_DATE"
        private const val OVERVIEW = "OVERVIEW"
    }
}