package com.swt.augmentmycampus

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.integration.android.IntentIntegrator
import com.swt.augmentmycampus.ui.camera.CameraFragment
import com.swt.augmentmycampus.ui.data.DataFragment
import com.swt.augmentmycampus.ui.search.SearchFragment
import com.swt.augmentmycampus.ui.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var navController: NavController;

    val cameraFragment : CameraFragment = CameraFragment()
    val dataFragment : DataFragment = DataFragment()
    val settingsFragment : SettingsFragment = SettingsFragment()
    val searchFragment : SearchFragment = SearchFragment()
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

    fun registerButtonOnClick(view: View) {
        val registerButton: Button = view.findViewById(R.id.registerButton)
        if (registerButton.text == "Register") {
            registerButton.text = "Unregister"
        } else {
            registerButton.text = "Register"
        }
    }

    fun flashButtonOnClick(view: View)
    {
        var nav: NavHostFragment =(fragmentManager.findFragmentById(R.id.nav_host_fragment)) as NavHostFragment
        var frag: CameraFragment = nav.childFragmentManager.primaryNavigationFragment as CameraFragment
        frag.toggleFlash(view)
    }


}