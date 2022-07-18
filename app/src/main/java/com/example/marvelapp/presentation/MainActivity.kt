package com.example.marvelapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarApp)

        firebaseAnalytics = Firebase.analytics
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
        Firebase.crashlytics.log("MainActivity - onCreate")

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavMain.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.charactersFragment,
                R.id.favoritesFragment,
                R.id.aboutFragment,
                R.id.sortFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.toolbarApp.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopLeveDestination = appBarConfiguration
                .topLevelDestinations.contains(destination.id)
            if (!isTopLeveDestination) {
                binding.toolbarApp.setNavigationIcon(R.drawable.ic_back)
            }
        }
    }
}