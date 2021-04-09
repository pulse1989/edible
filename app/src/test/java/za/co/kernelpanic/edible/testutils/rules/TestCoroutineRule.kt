package za.co.kernelpanic.edible.testutils.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {

    val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement?, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(testCoroutineDispatcher)

            base?.evaluate()
            Dispatchers.resetMain()
            testCoroutineDispatcher.cleanupTestCoroutines()
        }
    }

    //we use this rule when we;re testing 'launch' coroutine functions
    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        testCoroutineScope.runBlockingTest { block() }
    }
}