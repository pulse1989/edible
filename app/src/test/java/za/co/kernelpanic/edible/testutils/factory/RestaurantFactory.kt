package za.co.kernelpanic.edible.testutils.factory

import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile
import za.co.kernelpanic.edible.data.model.remote.Restaurants
import za.co.kernelpanic.edible.data.model.remote.SortingValues

object RestaurantFactory {

    fun generateRestaurantList(numberOfRestaurants: Int): List<CachedRestaurantProfile> {
        val restaurantList = mutableListOf<CachedRestaurantProfile>()
        repeat(numberOfRestaurants) {
            restaurantList.add(
                CachedRestaurantProfile(
                    restaurantId = DataFactory.randomInt(),
                    name = DataFactory.randomString(),
                    status = DataFactory.randomString(),
                    imageUrl = DataFactory.randomString(),
                    isFavourite = DataFactory.randomBoolean(),
                    bestMatch = DataFactory.randomDouble(),
                    newest = DataFactory.randomDouble(),
                    ratingAverage = DataFactory.randomDouble(),
                    distance = DataFactory.randomInt(),
                    popularity = DataFactory.randomDouble(),
                    averageProductPrice = DataFactory.randomInt(),
                    deliveryCost = DataFactory.randomInt(),
                    minCost = DataFactory.randomInt()
                )
            )
        }
        return restaurantList.toList()
    }

    fun generateRestaurantListNoFavs(numberOfRestaurants: Int): List<CachedRestaurantProfile> {
        val restaurantList = mutableListOf<CachedRestaurantProfile>()
        repeat(numberOfRestaurants) {
            restaurantList.add(
                CachedRestaurantProfile(
                    restaurantId = DataFactory.randomInt(),
                    name = DataFactory.randomString(),
                    status = DataFactory.randomString(),
                    imageUrl = DataFactory.randomString(),
                    isFavourite = false,
                    bestMatch = DataFactory.randomDouble(),
                    newest = DataFactory.randomDouble(),
                    ratingAverage = DataFactory.randomDouble(),
                    distance = DataFactory.randomInt(),
                    popularity = DataFactory.randomDouble(),
                    averageProductPrice = DataFactory.randomInt(),
                    deliveryCost = DataFactory.randomInt(),
                    minCost = DataFactory.randomInt()
                )
            )
        }
        return restaurantList.toList()
    }

    fun generateRestaurantListWithFavs(numberOfRestaurants: Int): List<CachedRestaurantProfile> {
        val restaurantList = mutableListOf<CachedRestaurantProfile>()
        repeat(numberOfRestaurants) {
            restaurantList.add(
                CachedRestaurantProfile(
                    restaurantId = DataFactory.randomInt(),
                    name = DataFactory.randomString(),
                    status = DataFactory.randomString(),
                    imageUrl = DataFactory.randomString(),
                    isFavourite = true,
                    bestMatch = DataFactory.randomDouble(),
                    newest = DataFactory.randomDouble(),
                    ratingAverage = DataFactory.randomDouble(),
                    distance = DataFactory.randomInt(),
                    popularity = DataFactory.randomDouble(),
                    averageProductPrice = DataFactory.randomInt(),
                    deliveryCost = DataFactory.randomInt(),
                    minCost = DataFactory.randomInt()
                )
            )
        }
        return restaurantList.toList()
    }

    fun generateRemoteRestaurants(numberOfRestaurants: Int): List<Restaurants> {
        val restaurantList = mutableListOf<Restaurants>()
        repeat(numberOfRestaurants) {
            restaurantList.add(
                Restaurants(
                    name = DataFactory.randomString(),
                    status = DataFactory.randomString(),
                    imageUrl = DataFactory.randomString(),
                    sortingValues = SortingValues(
                        bestMatch = DataFactory.randomDouble(),
                        newest = DataFactory.randomDouble(),
                        ratingAverage = DataFactory.randomDouble(),
                        distance = DataFactory.randomInt(),
                        popularity = DataFactory.randomDouble(),
                        averageProductPrice = DataFactory.randomInt(),
                        deliveryCosts = DataFactory.randomInt(),
                        minCost = DataFactory.randomInt()
                    )
                )
            )
        }
        return restaurantList.toList()
    }
}