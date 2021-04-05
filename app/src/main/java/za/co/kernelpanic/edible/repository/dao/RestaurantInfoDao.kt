package za.co.kernelpanic.edible.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile

@Dao
interface RestaurantInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurantData(restaurantData: List<CachedRestaurantProfile>)

    @Query("SELECT * FROM restaurants")
    fun getAllRestaurants(): Flow<List<CachedRestaurantProfile>>

    @Query("UPDATE restaurants set favourite = :markAsFavourite WHERE restaurant_id = :restaurantId")
    suspend fun markRestaurantAsFavourite(restaurantId: Int, markAsFavourite: Boolean)

    @Query("SELECT * from restaurants where favourite = :isFavourite")
    fun getFavouriteRestaurants(isFavourite: Boolean): Flow<List<CachedRestaurantProfile>>

    @Query("DELETE FROM restaurants")
    suspend fun clearAllRestaurantData()
}