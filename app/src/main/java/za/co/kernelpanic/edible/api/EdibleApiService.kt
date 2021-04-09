package za.co.kernelpanic.edible.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import za.co.kernelpanic.edible.app.EdibleApp
import za.co.kernelpanic.edible.data.model.remote.RestaurantData

/**
 * We use this class to read our json file and parse it into our data classes,
 * simulating an external api
 */
object EdibleApiService {

    fun getRestaurantData(fileName: String): RestaurantData? {
        val context = EdibleApp.getInstance()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val myJson = context.assets.open(fileName).bufferedReader().use { it.readText() }
        println(myJson)
        val adapter: JsonAdapter<RestaurantData> = moshi.adapter(RestaurantData::class.java)
        return adapter.fromJson(myJson)
    }
}