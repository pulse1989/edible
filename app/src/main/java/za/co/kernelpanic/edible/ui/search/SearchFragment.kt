package za.co.kernelpanic.edible.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import za.co.kernelpanic.edible.R
import za.co.kernelpanic.edible.data.model.cache.CachedRestaurantProfile
import za.co.kernelpanic.edible.databinding.FragmentSearchBinding
import za.co.kernelpanic.edible.ui.adapters.OnRestaurantClickListener
import za.co.kernelpanic.edible.ui.adapters.RestaurantsRecyclerViewAdapter
import za.co.kernelpanic.edible.ui.containerview.MainActivitySharedViewModel
import za.co.kernelpanic.edible.utils.showSnackBar
import za.co.kernelpanic.edible.utils.tagView

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment(), OnRestaurantClickListener {

    private val viewModel: MainActivitySharedViewModel by viewModels { defaultViewModelProviderFactory }
    private var _binding: FragmentSearchBinding? = null
    private lateinit var restaurantsAdapter: RestaurantsRecyclerViewAdapter
    private val binding get() = _binding!!
    private var uiStateJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tagView()
        initObservers()
        initListeners()
        binding.searchForRestaurantTextView.isVisible = true

    }

    private fun initObservers() {
        viewModel.searchLiveData.observe(viewLifecycleOwner, { restaurantList ->
            val errorText = getString(R.string.text_error_loading_data)

            binding.noRestaurantsTextView.isVisible = restaurantList.isEmpty()
            if (restaurantList.isNotEmpty()) {
                binding.recyclerView.isVisible = true
                initAdapter(restaurantList)
            } else {
                binding.recyclerView.isVisible = false
            }
        })
    }

    private fun initListeners() {
        binding.searchViewWidget.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    binding.searchForRestaurantTextView.isVisible = false
                    viewModel.setSearchQuery(newText)
                } else {
                    initAdapter(emptyList())
                }
                return true
            }
        })
        binding.searchViewWidget.requestFocus()
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