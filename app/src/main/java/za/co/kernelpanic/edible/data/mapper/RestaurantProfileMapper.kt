package za.co.kernelpanic.edible.data.mapper

import za.co.kernelpanic.edible.data.cache.CachedRestaurantProfile
import za.co.kernelpanic.edible.data.remote.Restaurants

class RestaurantProfileMapper : Mapper<Restaurants, CachedRestaurantProfile> {
    override fun mapToCache(remote: Restaurants): CachedRestaurantProfile {
        TODO("Not yet implemented")
    }

    override fun mapToRemote(cache: CachedRestaurantProfile): Restaurants {
        TODO("Not yet implemented")
    }

}