package za.co.kernelpanic.edible.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import za.co.kernelpanic.edible.R
import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile
import za.co.kernelpanic.edible.databinding.FragmentFavouritesBinding
import za.co.kernelpanic.edible.ui.adapters.OnRestaurantClickListener
import za.co.kernelpanic.edible.ui.adapters.RestaurantsRecyclerViewAdapter
import za.co.kernelpanic.edible.ui.containerview.MainActivitySharedViewModel
import za.co.kernelpanic.edible.utils.showSnackBar
import za.co.kernelpanic.edible.utils.tagView

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavouritesFragment : Fragment(), OnRestaurantClickListener {

    private val viewModel: MainActivitySharedViewModel by viewModels { defaultViewModelProviderFactory }

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private var uiStateJob: Job? = null

    private lateinit var restaurantsAdapter: RestaurantsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tagView()
        loadBookmarkedRestaurants()
    }

    private fun loadBookmarkedRestaurants() {
        uiStateJob?.cancel()
        renderUI()
        uiStateJob = lifecycleScope.launch {
            viewModel.getBookmarkedRestaurants()
        }
    }

    private fun renderUI() {
        viewModel.bookmarkedRestaurantsLiveData.observe(viewLifecycleOwner, { viewState ->
            binding.loadingIndicator.isVisible = viewState.isLoading

            viewState.restaurants?.let { restaurantList ->
                binding.noRestaurantsTextView.isVisible = restaurantList.isEmpty()

                if (restaurantList.isNotEmpty()) {
                    initAdapter(restaurantList)
                } else {
                    binding.recyclerView.isVisible = false
                }
            }

            val errorText = getString(R.string.text_error_loading_data)
            viewState.error?.let {
                showSnackBar(
                    it.message ?: errorText,
                    binding.coordinatorLayoutParent
                )
            }
        })
    }

    private fun initAdapter(restaurants: List<CachedRestaurantProfile>) {
        restaurantsAdapter = RestaurantsRecyclerViewAdapter(restaurants, this)
        binding.recyclerView.apply {
            val adapterLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = adapterLayoutManager
            adapter = restaurantsAdapter
        }
    }

    override fun onStop() {
        uiStateJob?.cancel()
        super.onStop()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun setFavourite(restaurantId: Int) {
        viewModel.bookmarkRestaurant(restaurantId = restaurantId)
    }

    override fun removeFavourite(restaurantId: Int) {
        viewModel.unBookmarkRestaurant(restaurantId = restaurantId)
    }

    override fun onStatusClick(statusMessage: String) {
        showSnackBar(statusMessage, binding.coordinatorLayoutParent)
    }
}