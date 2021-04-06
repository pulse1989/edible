package za.co.kernelpanic.edible.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile
import za.co.kernelpanic.edible.databinding.ListItemRestaurantsBinding

class RestaurantsViewHolder(private val binding: ListItemRestaurantsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(restaurant: CachedRestaurantProfile) {

    }

    companion object {
        internal fun create(parent: ViewGroup): RestaurantsViewHolder {
            val binding: ListItemRestaurantsBinding =
                ListItemRestaurantsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return RestaurantsViewHolder(binding)
        }
    }
}