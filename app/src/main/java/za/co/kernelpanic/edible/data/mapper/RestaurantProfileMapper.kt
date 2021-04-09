package za.co.kernelpanic.edible.data.mapper

import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile
import za.co.kernelpanic.edible.data.model.remote.Restaurants

object RestaurantProfileMapper : Mapper<Restaurants, CachedRestaurantProfile> {

    override fun mapToCache(remote: Restaurants): CachedRestaurantProfile {
        return CachedRestaurantProfile(
            name = remote.name,
            imageUrl = remote.imageUrl,
            status = remote.status,
            bestMatch = remote.sortingValues.bestMatch,
            newest = remote.sortingValues.newest,
            ratingAverage = remote.sortingValues.ratingAverage,
            distance = remote.sortingValues.distance,
            popularity = remote.sortingValues.popularity,
            averageProductPrice = remote.sortingValues.averageProductPrice,
            deliveryCost = remote.sortingValues.deliveryCosts,
            minCost = remote.sortingValues.minCost
        )
    }
}