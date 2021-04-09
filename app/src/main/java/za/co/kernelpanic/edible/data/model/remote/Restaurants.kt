package za.co.kernelpanic.edible.data.model.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Restaurants(
    val name: String,
    val status: String,
    val imageUrl: String,
    val sortingValues: SortingValues
)