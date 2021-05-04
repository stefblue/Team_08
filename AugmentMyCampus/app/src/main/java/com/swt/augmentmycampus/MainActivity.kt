package com.swt.augmentmycampus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.main_activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //val navController = findNavController(R.id.nav_host_fragment)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navView.setupWithNavController(navController)
    }

    fun resetView(view: View) {
        //TODO
        Log.i("MainActivity", "reset")
        setContentView(R.layout.main_activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setupWithNavController(navController)
    }
}