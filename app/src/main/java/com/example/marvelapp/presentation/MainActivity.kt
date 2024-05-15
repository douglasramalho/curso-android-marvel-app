package com.example.marvelapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private val binding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

  private lateinit var navController: NavController
  private lateinit var appBarConfiguration: AppBarConfiguration

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    setupNavigation()
  }

  private fun setupNavigation() {
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.navHostContainer) as NavHostFragment

    navController = navHostFragment.navController

    binding.bottomNavMain.setupWithNavController(navController)

    appBarConfiguration = AppBarConfiguration(
      // Indica quais telas são top level destination
      setOf(
        R.id.charactersFragment,
        R.id.favoritesFragment,
        R.id.aboutFragment
      )
    )

    binding.toolbar.setupWithNavController(navController, appBarConfiguration)

    navController.addOnDestinationChangedListener { _, destination, _ ->
      val isTopLevelDestination = appBarConfiguration.topLevelDestinations.contains(destination.id)
      if (!isTopLevelDestination) {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
      }
    }
  }
}