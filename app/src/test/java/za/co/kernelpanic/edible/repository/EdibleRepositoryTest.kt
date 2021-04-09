package za.co.kernelpanic.edible.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import za.co.kernelpanic.edible.data.mapper.RestaurantProfileMapper
import za.co.kernelpanic.edible.testutils.factory.RestaurantFactory
import za.co.kernelpanic.edible.testutils.rules.TestCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class EdibleRepositoryTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineTestRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun cacheMapperReturnsMappedRestaurantValues() {
        val restaurants = RestaurantFactory.generateRemoteRestaurants(2)
        val cachedRestaurants = restaurants.map { RestaurantProfileMapper.mapToCache(it) }
        val favouriteCheck = cachedRestaurants[0].isFavourite

        assertEquals(false, favouriteCheck)
    }
}