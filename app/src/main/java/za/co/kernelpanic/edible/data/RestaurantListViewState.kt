package za.co.kernelpanic.edible.data

import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile

data class RestaurantListViewState(
    val isLoading: Boolean,
    val restaurants: List<CachedRestaurantProfile>?,
    val error: Throwable?
)