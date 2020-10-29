package com.itm.lolMatching

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.itm.lolMatching.databinding.ActivitySearchBinding
import kotlinx.android.synthetic.main.activity_search.view.*
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // 바보같이, setContent를 안해서 아무것도 안나온 거였음...

        val message = intent.getStringExtra(EXTRA_MESSAGE).toString()


        view.sommernerName.setText(message)


//        this.findNavController(R.id.)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

//        Log.d("123", message)

    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.test, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_to_mypage -> {
                // TODO : 어떤 동작을 할 것인지에 대해서
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}