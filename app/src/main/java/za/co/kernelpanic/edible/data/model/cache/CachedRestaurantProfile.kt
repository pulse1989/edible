package za.co.kernelpanic.edible.data.model.cache

import androidx.room.*

@Entity(tableName = "restaurants")
data class CachedRestaurantProfile(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "restaurant_id") val restaurantId: Long? = 0,
        @ColumnInfo(name = "name") val name: String? = "",
        @ColumnInfo(name = "status") val status: String? = "",
        @ColumnInfo(name = "favourite") val isFavourite: Boolean? = false,
        @ColumnInfo(name = "best_match") val bestMatch: Double? = 0.0,
        @ColumnInfo(name = "newest") val newest: Double? = 0.0,
        @ColumnInfo(name = "rating_average") val ratingAverage: Double? = 0.0,
        @ColumnInfo(name = "distance") val distance: Int? = 0,
        @ColumnInfo(name = "popularity") val popularity: Double? = 0.0,
        @ColumnInfo(name = "average_product_price") val averageProductPrice: Int? = 0,
        @ColumnInfo(name = "delivery_cost") val deliveryCost: Int? = 0,
        @ColumnInfo(name = "min_cost") val minCost: Int? = 0,
)