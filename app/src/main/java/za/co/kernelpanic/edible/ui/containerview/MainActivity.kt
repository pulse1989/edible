package za.co.kernelpanic.edible.ui.containerview

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import za.co.kernelpanic.edible.R
import za.co.kernelpanic.edible.databinding.ActivityMainBinding
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferences: SharedPreferences

    private val viewModel: MainActivitySharedViewModel by viewModels { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupNavController()
    }

    override fun onStart() {
        super.onStart()
        if (preferences.getBoolean("firstRun", true)) {
            lifecycleScope.launch { viewModel.initData() }
            with(preferences.edit()) {
                putBoolean("firstRun", false).apply()
            }
        }
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
                    as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfig = AppBarConfiguration(navController.graph)

        binding.dashboardToolbar.setupWithNavController(navController, appBarConfig)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}