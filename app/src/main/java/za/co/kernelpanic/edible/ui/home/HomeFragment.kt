package za.co.kernelpanic.edible.ui.home

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
import za.co.kernelpanic.edible.databinding.HomeFragmentBinding
import za.co.kernelpanic.edible.ui.adapters.OnRestaurantClickListener
import za.co.kernelpanic.edible.ui.adapters.RestaurantsRecyclerViewAdapter
import za.co.kernelpanic.edible.ui.containerview.MainActivitySharedViewModel
import za.co.kernelpanic.edible.utils.showSnackBar
import za.co.kernelpanic.edible.utils.tagView

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(), OnRestaurantClickListener {

    private val viewModel: MainActivitySharedViewModel by viewModels { defaultViewModelProviderFactory }

    private lateinit var restaurantsAdapter: RestaurantsRecyclerViewAdapter
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private var uiStateJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tagView()
        loadData()
    }

    private fun loadData() {
        render()
        uiStateJob?.cancel()
        uiStateJob = lifecycleScope.launch { viewModel.getRestaurants() }
    }

    private fun render() {
        viewModel.restaurantsLiveData.observe(viewLifecycleOwner, { viewState ->
            val errorText = getString(R.string.text_error_loading_data)
            binding.loadingIndicator.isVisible = viewState.isLoading

            viewState.restaurants?.let { restaurantList ->
                binding.noRestaurantsTextView.isVisible = restaurantList.isEmpty()
                if (restaurantList.isNotEmpty()) {
                    initAdapter(restaurantList)
                }
            }

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

    override fun setFavourite(restaurantId: Int) {
        viewModel.bookmarkRestaurant(restaurantId = restaurantId)
    }

    override fun removeFavourite(restaurantId: Int) {
        viewModel.unBookmarkRestaurant(restaurantId = restaurantId)
    }

    override fun onStatusClick(statusMessage: String) {
        showSnackBar(statusMessage, binding.coordinatorLayoutParent)
    }

    override fun onStop() {
        uiStateJob?.cancel()
        super.onStop()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}