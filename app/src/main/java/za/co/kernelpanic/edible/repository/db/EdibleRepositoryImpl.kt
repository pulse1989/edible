package za.co.kernelpanic.edible.repository.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import za.co.kernelpanic.edible.api.EdibleApiService
import za.co.kernelpanic.edible.data.mapper.RestaurantProfileMapper
import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile
import za.co.kernelpanic.edible.data.model.remote.RestaurantData
import za.co.kernelpanic.edible.data.model.remote.Restaurants
import za.co.kernelpanic.edible.di.DispatcherProvider
import za.co.kernelpanic.edible.repository.EdibleRepository
import za.co.kernelpanic.edible.repository.dao.RestaurantInfoDao
import javax.inject.Inject

class EdibleRepositoryImpl @Inject constructor(
    private val dao: RestaurantInfoDao,
    private val dispatcherProvider: DispatcherProvider
) :
    EdibleRepository {

    override suspend fun getRestaurantsFromRemote(fileName: String): RestaurantData {
        return EdibleApiService.getRestaurantData(fileName) ?: RestaurantData(emptyList())
    }

    override suspend fun saveRestaurants(restaurantList: List<Restaurants>) {
        val mappedEateries = restaurantList.map { RestaurantProfileMapper.mapToCache(it) }
        dao.insertRestaurantData(mappedEateries)
    }

    override suspend fun getCachedRestaurants(): Flow<List<CachedRestaurantProfile>> {
        return dao.getAllRestaurants()
    }

    override suspend fun setRestaurantAsFavourite(restaurantId: Int) {
        dao.markRestaurantAsFavourite(restaurantId = restaurantId, markAsFavourite = true)
    }

    override suspend fun removeRestaurantAsFavourite(restaurantId: Int) {
        dao.unMarkRestaurantAsFavourite(restaurantId = restaurantId, markAsFavourite = false)
    }

    override suspend fun getFavouriteRestaurants(): Flow<List<CachedRestaurantProfile>> {
        return dao.getFavouriteRestaurants()
    }

    override suspend fun searchRestaurant(searchQuery: String): Flow<List<CachedRestaurantProfile>> {
        return dao.searchRestaurant(searchQuery).flowOn(dispatcherProvider.default()).conflate()
    }

    override suspend fun clearCachedData() = dao.clearAllRestaurantData()
}