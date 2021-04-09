package za.co.kernelpanic.edible.ui.containerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import za.co.kernelpanic.edible.data.RestaurantListViewState
import za.co.kernelpanic.edible.repository.EdibleRepository
import za.co.kernelpanic.edible.utils.asLiveData
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainActivitySharedViewModel @Inject constructor(private val repository: EdibleRepository) :
    ViewModel() {

    private val restaurantsListMutableLiveData = MutableLiveData<RestaurantListViewState>()
    internal val restaurantsLiveData = restaurantsListMutableLiveData.asLiveData()
    private val bookmarkedRestaurantsMutableLiveData = MutableLiveData<RestaurantListViewState>()
    internal val bookmarkedRestaurantsLiveData = bookmarkedRestaurantsMutableLiveData.asLiveData()

    private val searchFlow = MutableSharedFlow<String>()

    internal fun initData() {
        viewModelScope.launch { downloadRestaurantData() }
    }

    internal fun setSearchQuery(searchQuery: String) {
        viewModelScope.launch { searchFlow.emit(searchQuery) }
    }

    internal val searchLiveData = searchFlow.asSharedFlow().flatMapLatest { search ->
        repository.searchRestaurant(search)
    }.catch {
        restaurantsListMutableLiveData.postValue(
            RestaurantListViewState(
                isLoading = false,
                null,
                error = Throwable("Unable to load data")
            )
        )
    }.asLiveData()

    internal suspend fun getRestaurants() {
        repository.getCachedRestaurants()
            .onEmpty {
                restaurantsListMutableLiveData.postValue(
                    RestaurantListViewState(
                        isLoading = false,
                        restaurants = null,
                        error = null
                    )
                )
            }
            .catch {
                restaurantsListMutableLiveData.postValue(
                    RestaurantListViewState(
                        isLoading = false,
                        restaurants = null,
                        error = Throwable("Unable to load data")
                    )
                )
            }
            .onStart {
                restaurantsListMutableLiveData.postValue(
                    RestaurantListViewState(
                        isLoading = true, restaurants = null, error = null
                    )
                )
            }.stateIn(viewModelScope)
            .collect { restaurantList ->
                restaurantsListMutableLiveData.postValue(
                    RestaurantListViewState(
                        isLoading = false,
                        restaurants = restaurantList,
                        error = null
                    )
                )
            }
    }

    private suspend fun downloadRestaurantData() {
        val data = repository.getRestaurantsFromRemote("takeaway.json")
        repository.saveRestaurants(data.restaurants)
    }

    internal fun bookmarkRestaurant(restaurantId: Int) {
        viewModelScope.launch { repository.setRestaurantAsFavourite(restaurantId) }
    }

    internal fun unBookmarkRestaurant(restaurantId: Int) {
        viewModelScope.launch { repository.removeRestaurantAsFavourite(restaurantId = restaurantId) }
    }

    internal suspend fun getBookmarkedRestaurants() {
        repository.getFavouriteRestaurants()
            .onEmpty {
                bookmarkedRestaurantsMutableLiveData.postValue(
                    RestaurantListViewState(
                        isLoading = false,
                        restaurants = null,
                        null
                    )
                )
            }.catch {
                bookmarkedRestaurantsMutableLiveData.postValue(
                    RestaurantListViewState(
                        isLoading = false,
                        restaurants = null,
                        error = Throwable("Unable to load data")
                    )
                )
            }
            .onStart {
                bookmarkedRestaurantsMutableLiveData.postValue(
                    RestaurantListViewState(
                        isLoading = true,
                        restaurants = null,
                        null
                    )
                )
            }
            .stateIn(viewModelScope)
            .collect { restaurantList ->
                bookmarkedRestaurantsMutableLiveData.postValue(
                    RestaurantListViewState(
                        isLoading = false,
                        restaurants = restaurantList,
                        null
                    )
                )
            }
    }
}