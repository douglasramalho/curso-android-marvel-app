package com.example.marvelapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment


        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf()
        )


    }
}