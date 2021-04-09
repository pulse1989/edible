package za.co.kernelpanic.edible.repository

import kotlinx.coroutines.flow.Flow
import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile
import za.co.kernelpanic.edible.data.model.remote.RestaurantData
import za.co.kernelpanic.edible.data.model.remote.Restaurants

interface EdibleRepository {

    suspend fun getRestaurantsFromRemote(fileName: String): RestaurantData
    suspend fun saveRestaurants(restaurantList: List<Restaurants>)
    suspend fun getCachedRestaurants(): Flow<List<CachedRestaurantProfile>>
    suspend fun setRestaurantAsFavourite(restaurantId: Int)
    suspend fun removeRestaurantAsFavourite(restaurantId: Int)
    suspend fun getFavouriteRestaurants(): Flow<List<CachedRestaurantProfile>>
    suspend fun searchRestaurant(searchQuery: String): Flow<List<CachedRestaurantProfile>>
    suspend fun clearCachedData()
}