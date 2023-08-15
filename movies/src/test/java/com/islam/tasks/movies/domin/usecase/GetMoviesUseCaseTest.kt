package com.islam.tasks.movies.domin.usecase

import com.islam.tasks.movies.domain.entity.MovieEntity
import com.islam.tasks.movies.domain.entity.exception.EmptyMoviesException
import com.islam.tasks.movies.domain.repository.MoviesRepository
import com.islam.tasks.movies.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class GetMoviesUseCaseTest {
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Mock
    private lateinit var repository: MoviesRepository
    private val testScheduler = TestCoroutineScheduler()
    private lateinit var dispatcher: TestDispatcher

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        getMoviesUseCase = GetMoviesUseCase(repository)
    }

    @Test
    fun `executing GetMoviesUseCase, when the api returns null movies list it will throw an exception and the useCase will return empty list`() =
        runTest {
            //Arrange
            whenever(repository.getMovies()).thenThrow(EmptyMoviesException())

            //Action
            val result = getMoviesUseCase()

            advanceUntilIdle()

            //Assert
            Assert.assertEquals(result, emptyList<MovieEntity>())
        }
}