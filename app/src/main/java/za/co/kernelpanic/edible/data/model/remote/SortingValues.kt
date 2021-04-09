package za.co.kernelpanic.edible.data.model.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SortingValues(
    val bestMatch: Double,
    val newest: Double,
    val ratingAverage: Double,
    val distance: Int,
    val popularity: Double,
    val averageProductPrice: Int,
    val deliveryCosts: Int,
    val minCost: Int
)