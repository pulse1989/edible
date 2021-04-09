package za.co.kernelpanic.edible.data.model

import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile

sealed class RestaurantListViewState {
    object Loading : RestaurantListViewState()
    data class Success(val response: List<CachedRestaurantProfile>) : RestaurantListViewState()
    data class Failure(val error: Throwable) : RestaurantListViewState()
}
