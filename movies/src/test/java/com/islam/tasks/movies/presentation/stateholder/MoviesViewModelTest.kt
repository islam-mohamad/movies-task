package com.islam.tasks.movies.presentation.stateholder

import com.islam.tasks.core.R
import com.islam.tasks.core.test.test
import com.islam.tasks.movies.domain.entity.MovieEntity
import com.islam.tasks.movies.domain.usecase.GetMoviesUseCase
import com.islam.tasks.movies.presentation.uimodel.MovieUiModel
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
class MoviesViewModelTest {
    private lateinit var viewModel: MoviesViewModel

    @Mock
    private lateinit var getMoviesUseCase: GetMoviesUseCase
    private val testScheduler = TestCoroutineScheduler()
    private lateinit var dispatcher: TestDispatcher

    @Mock
    private lateinit var movieEntity: MovieEntity

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        viewModel = MoviesViewModel(
            dispatcher = dispatcher, getMoviesUseCase = getMoviesUseCase
        )
    }

    @Test
    fun `executing GetDetails function,  getMoviesUseCase should be invoked and return success`() =
        runTest {
            //Arrange
            whenever(getMoviesUseCase.invoke()).thenReturn(listOf(movieEntity))
            whenever(movieEntity.id).thenReturn(MOVIE_ID)
            whenever(movieEntity.title).thenReturn(TITLE)
            whenever(movieEntity.releaseDate).thenReturn(RELEASE_DATE)
            whenever(movieEntity.posterPath).thenReturn(POSTER_PATH)
            val observer = viewModel.loadingChannel.test(this)

            //Action
            viewModel.getMovies()
            advanceUntilIdle()

            //Assert
            observer.assertValuesAndFinish(true, false)
            assertEquals(
                viewModel.moviesStateFlow.value, listOf(
                    MovieUiModel(
                        id = MOVIE_ID,
                        title = TITLE,
                        posterPath = POSTER_PATH,
                        releaseDate = RELEASE_DATE
                    )
                )
            )
        }

    @Test
    fun `executing GetDetails,  and if getMoviesUseCase throws an exception it should return Error`() =
        runTest {
            //Arrange
            whenever(getMoviesUseCase.invoke()).thenThrow(RuntimeException())

            val loadingObserver = viewModel.loadingChannel.test(this)
            val errorObserver = viewModel.errorResChannel.test(this)

            //Action
            viewModel.getMovies()
            advanceUntilIdle()

            //Assert
            loadingObserver.assertValuesAndFinish(true, false)
            errorObserver.assertValuesAndFinish(R.string.general_error)
        }

    companion object {
        private const val MOVIE_ID = 0
        private const val TITLE = "TITLE"
        private const val POSTER_PATH = "POSTER_PATH"
        private const val RELEASE_DATE = "RELEASE_DATE"
    }
}