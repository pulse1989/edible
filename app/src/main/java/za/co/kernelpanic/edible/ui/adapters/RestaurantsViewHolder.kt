package za.co.kernelpanic.edible.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import za.co.kernelpanic.edible.R
import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile
import za.co.kernelpanic.edible.databinding.ListItemRestaurantsBinding
import za.co.kernelpanic.edible.utils.formatDistanceInKm
import za.co.kernelpanic.edible.utils.toCurrencyFormat

class RestaurantsViewHolder(
    private val binding: ListItemRestaurantsBinding,
    private val listener: OnRestaurantClickListener,
    private val context: Context
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(restaurant: CachedRestaurantProfile) {
        binding.restaurantNameTextView.text = restaurant.name
        binding.restaurantImageView.load(restaurant.imageUrl)
        binding.deliveryFeeTextView.text = restaurant.deliveryCost?.toCurrencyFormat()
        binding.starRatingTextView.text = restaurant.ratingAverage?.toString()
        binding.distanceTextView.text = restaurant.distance?.formatDistanceInKm().toString()

        if (restaurant.isFavourite) {
            binding.favouriteIndicatorImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_selected_favourite
                )
            )
        } else {
            binding.favouriteIndicatorImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_favorites
                )
            )
        }

        binding.favouriteIndicatorImageView.setOnClickListener {
            if (restaurant.isFavourite) {
                listener.removeFavourite(restaurantId = restaurant.restaurantId)
            } else {
                listener.setFavourite(restaurantId = restaurant.restaurantId)
            }
        }

        when (restaurant.status) {
            "open" -> {
                binding.statusIndicatorImageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.online_indicator
                    )
                )
                binding.statusIndicatorImageView.setOnClickListener {
                    listener.onStatusClick(context.getString(R.string.text_status_restaurant_open))
                }

            }
            "closed" -> {
                binding.statusIndicatorImageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.offline_indicator
                    )
                )
                binding.statusIndicatorImageView.setOnClickListener {
                    listener.onStatusClick(context.getString(R.string.text_status_restaurant_closed))
                }

            }
            else -> {
                binding.statusIndicatorImageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.order_ahead_indicator
                    )
                )
                binding.statusIndicatorImageView.setOnClickListener {
                    listener.onStatusClick(context.getString(R.string.text_status_restaurant_order_ahead))
                }
            }
        }
    }

    companion object {
        internal fun create(
            parent: ViewGroup,
            listener: OnRestaurantClickListener
        ): RestaurantsViewHolder {
            val binding: ListItemRestaurantsBinding =
                ListItemRestaurantsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return RestaurantsViewHolder(binding, listener, parent.context)
        }
    }
}