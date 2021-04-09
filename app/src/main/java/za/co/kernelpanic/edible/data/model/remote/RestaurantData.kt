package za.co.kernelpanic.edible.data.model.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestaurantData(val restaurants: List<Restaurants>)