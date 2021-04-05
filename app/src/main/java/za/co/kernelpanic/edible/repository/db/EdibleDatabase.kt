package za.co.kernelpanic.edible.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile
import za.co.kernelpanic.edible.repository.dao.RestaurantInfoDao

@Database(entities = [CachedRestaurantProfile::class], version = 1, exportSchema = true)
abstract class EdibleDatabase: RoomDatabase() {

    abstract fun restaurantInfoDao(): RestaurantInfoDao
}