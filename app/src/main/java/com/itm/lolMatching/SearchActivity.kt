package com.itm.lolMatching

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import com.itm.lolMatching.databinding.ActivitySearchBinding
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root) // 바보같이, setContent를 안해서 아무것도 안나온 거였음...
        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

//        Log.d("123", message)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }


}