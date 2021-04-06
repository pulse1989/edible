package za.co.kernelpanic.edible.data.model.remote

data class Restaurants(
    val name: String,
    val status: String,
    val imageUrl: String,
    val sortingValues: SortingValues
)