package za.co.kernelpanic.edible.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import za.co.kernelpanic.edible.repository.EdibleRepository
import za.co.kernelpanic.edible.repository.dao.RestaurantInfoDao
import za.co.kernelpanic.edible.testutils.rules.TestCoroutineRule

@ExperimentalCoroutinesApi
class MainActivitySharedViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineTestRule = TestCoroutineRule()

    @MockK
    private lateinit var repo: EdibleRepository

    @MockK
    private lateinit var databaseDao: RestaurantInfoDao

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    fun getRestaurantProvidesRestaurantToUI() {
        coroutineTestRule.runBlockingTest {
            repo
        }
    }
}