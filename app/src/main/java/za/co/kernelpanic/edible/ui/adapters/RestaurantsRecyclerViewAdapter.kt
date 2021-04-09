package za.co.kernelpanic.edible.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile

class RestaurantsRecyclerViewAdapter(
    private val restaurants: List<CachedRestaurantProfile>,
    private val listener: OnRestaurantClickListener
) :
    RecyclerView.Adapter<RestaurantsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        return RestaurantsViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        holder.bind(restaurant = restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size
}