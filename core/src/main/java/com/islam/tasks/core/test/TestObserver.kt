package com.islam.tasks.core.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
/**
 * Test observer for Flow to be able to capture and verify all states.
 */
class TestObserver<T>(
    scope: CoroutineScope,
    testScheduler: TestCoroutineScheduler,
    flow: Flow<T>
) {
    private val values = mutableListOf<T>()

    private val job: Job = scope.launch(UnconfinedTestDispatcher(testScheduler)) {
        flow.collect { values.add(it) }
    }

    /**
     * Assert no values
     */
    fun assertNoValues(): TestObserver<T> {
        assertEquals(emptyList<T>(), this.values)
        return this
    }

    /**
     * Assert the values. Important [TestObserver.finish] needs to be called at the end of the test.
     */
    fun assertValues(vararg values: T): TestObserver<T> {
        assertEquals(values.toList(), this.values)
        return this
    }

    /**
     * Assert the values and finish. Convenient to avoid having to call finish if done last in the test.
     */
    fun assertValuesAndFinish(vararg values: T): TestObserver<T> {
        assertEquals(values.toList(), this.values)
        finish()
        return this
    }

    /**
     * Finish the job
     */
    fun finish() {
        job.cancel()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
        /**
         * Test function for the [TestObserver]
         */

fun <T> Flow<T>.test(
    scope: TestScope
): TestObserver<T> {
    return TestObserver(scope, scope.testScheduler, this)
}