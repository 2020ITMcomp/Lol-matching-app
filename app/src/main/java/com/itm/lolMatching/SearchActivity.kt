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

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        drawerLayout = binding.drawerLayout
        setContentView(binding.root) // 바보같이, setContent를 안해서 아무것도 안나온 거였음...

//        val navController = this.findNavController(R.id.nav_graph)

//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        NavigationUI.setupWithNavController(binding.navView, navController)

//        val appVarConfiguration = AppBarConfiguration(navController.graph)
//        val message = intent.getStringExtra(EXTRA_MESSAGE).toString()

//        Log.d("123", message)

    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.nav_graph)
//        return NavigationUI.navigateUp(navController, drawerLayout)
//    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.test, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle item selection
//        return when (item.itemId) {
//            R.id.action_to_mypage -> {
//                // TODO : 어떤 동작을 할 것인지에 대해서
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

}