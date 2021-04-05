package za.co.kernelpanic.edible.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import za.co.kernelpanic.edible.repository.dao.RestaurantInfoDao
import za.co.kernelpanic.edible.repository.db.EdibleDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext appContext: Context): EdibleDatabase {
        return Room.databaseBuilder(
            appContext,
            EdibleDatabase::class.java, "edible.db"
        ).build()
    }

    @Provides
    fun providesRestaurantInfoDao(appDatabase: EdibleDatabase): RestaurantInfoDao {
        return appDatabase.restaurantInfoDao()
    }

    @Provides
    @Singleton
    fun providesEncryptedSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context.applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        val sharedPrefsFile = "ediblePref"

        return EncryptedSharedPreferences.create(
            context,
            sharedPrefsFile,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}