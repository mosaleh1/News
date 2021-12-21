package com.runcode.news

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.runcode.news.R
import com.runcode.news.databinding.ActivityMainBinding
import com.runcode.news.databinding.ActivityMainBinding.inflate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding  : ActivityMainBinding= inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{
            _,destination,_ ->
            if (destination.id == R.id.newsProfileFragment){
                binding.bottomNavigation.visibility = View.GONE
            }else{
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }


//        if (!(isPhoneConnected())) {
//            Log.d("TAG", "onCreate:  ")
//            binding.bottomNavigation.visibility = View.INVISIBLE
//            binding.navHostFragment.visibility = View.INVISIBLE
//            binding.offlineImage.visibility = View.VISIBLE
//            binding.offlineText.visibility = View.VISIBLE
//        }
    }

    private fun isPhoneConnected(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }
}