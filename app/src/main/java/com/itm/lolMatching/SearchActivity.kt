package com.itm.lolMatching

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itm.lolMatching.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root

        val message = intent.getStringExtra(EXTRA_MESSAGE)

        binding.sommernerName.text = message

    }
}