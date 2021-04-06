package za.co.kernelpanic.edible.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile

class RestaurantsRecyclerView(private val restaurants: List<CachedRestaurantProfile>) :
    RecyclerView.Adapter<RestaurantsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        return RestaurantsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        holder.bind(restaurant = restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size
}