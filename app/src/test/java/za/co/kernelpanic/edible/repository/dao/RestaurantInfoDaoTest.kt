package za.co.kernelpanic.edible.repository.dao

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile
import za.co.kernelpanic.edible.repository.db.EdibleDatabase
import za.co.kernelpanic.edible.testutils.factory.RestaurantFactory
import za.co.kernelpanic.edible.testutils.rules.TestCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class RestaurantInfoDaoTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineTestRule = TestCoroutineRule()

    private lateinit var database: EdibleDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            EdibleDatabase::class.java
        ).setQueryExecutor(coroutineTestRule.testCoroutineDispatcher.asExecutor())
            .setTransactionExecutor(coroutineTestRule.testCoroutineDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()
    }

    //current bug with runBlockingTest prohibits us from testing for more items
    //githublink: https://github.com/Kotlin/kotlinx.coroutines/issues/1204
    //jetbrains still working on the issue: https://github.com/Kotlin/kotlinx.coroutines/pull/1206
    @Test
    fun insertRestaurantDataShouldSaveData() {
        //given
        val restaurantList = RestaurantFactory.generateRestaurantList(1)
        //when
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            val result = async {
                database.restaurantInfoDao().insertRestaurantData(restaurantList)
                database.restaurantInfoDao().getAllRestaurants().first()
            }
            //then
            assertEquals(restaurantList, result.await())
        }
    }

    @Test
    fun markRestaurantAsFavouriteMarksRestaurantAsFavoriteInDb() {
        //given
        val restaurantList = RestaurantFactory.generateRestaurantListNoFavs(1)
        //when
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            val result = async {
                database.restaurantInfoDao().insertRestaurantData(restaurantList)
                database.restaurantInfoDao().markRestaurantAsFavourite(
                    restaurantList[0].restaurantId,
                    true
                )
                database.restaurantInfoDao().getFavouriteRestaurants().first()[0].isFavourite
            }
            //then
            assertEquals(true, result.await())
        }
    }

    @Test
    fun unmarkRestaurantAsFavouriteUnmarksFavouriteInDb() {
        //given
        val restaurantList = RestaurantFactory.generateRestaurantListWithFavs(1)
        //when
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            val result = async {
                database.restaurantInfoDao().insertRestaurantData(restaurantList)
                database.restaurantInfoDao()
                    .unMarkRestaurantAsFavourite(restaurantList[0].restaurantId, false)
                database.restaurantInfoDao().getAllRestaurants().first()[0].isFavourite
            }
            //then
            assertEquals(false, result.await())
        }
    }

    @Test
    fun shouldClearDataFromDatabaseWhenTriggered() {
        //given
        val restaurantList = RestaurantFactory.generateRestaurantList(10)

        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            //when
            database.restaurantInfoDao().insertRestaurantData(restaurantList)
            database.restaurantInfoDao().clearAllRestaurantData()

            //then
            val result = database.restaurantInfoDao().getAllRestaurants().first()
            assertEquals(emptyList<CachedRestaurantProfile>(), result)
        }
    }
}
