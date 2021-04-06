package za.co.kernelpanic.edible.ui.containerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import za.co.kernelpanic.edible.R
import za.co.kernelpanic.edible.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupNavController()
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id)
                as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfig = AppBarConfiguration(navController.graph)

        binding.dashboardToolbar.setupWithNavController(navController, appBarConfig)
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}