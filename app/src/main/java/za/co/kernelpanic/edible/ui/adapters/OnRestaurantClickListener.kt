package za.co.kernelpanic.edible.ui.adapters

interface OnRestaurantClickListener {

    fun setFavourite(restaurantId: Int)
    fun removeFavourite(restaurantId: Int)
    fun onStatusClick(statusMessage: String)
}