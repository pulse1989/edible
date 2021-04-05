package za.co.kernelpanic.edible.data.remote

data class SortingValues(
    val bestMatch: Float,
    val newest: Float,
    val ratingAverage: Float,
    val distance: Int,
    val popularity: Float,
    val averageProductPrice: Int,
    val deliveryCosts: Int,
    val minCost: Int
)