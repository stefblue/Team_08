package com.swt.augmentmycampus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.swt.augmentmycampus.businessLogic.DataBusinessLogic
import com.swt.augmentmycampus.businessLogic.UrlBusinessLogic
import com.swt.augmentmycampus.network.Webservice
import com.swt.augmentmycampus.ui.camera.CameraFragment
import com.swt.augmentmycampus.ui.data.DataFragment
import com.swt.augmentmycampus.ui.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.os.StrictMode

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController;

    val cameraFragment : CameraFragment = CameraFragment()
    val dataFragment : DataFragment = DataFragment()
    val settingsFragment : SettingsFragment = SettingsFragment()
    val fragmentManager : FragmentManager = supportFragmentManager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.main_activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }
}