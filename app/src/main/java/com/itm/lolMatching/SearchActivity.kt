package com.itm.lolMatching

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.itm.lolMatching.databinding.ActivitySearchBinding
import kotlinx.android.synthetic.main.activity_search.view.*

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // 바보같이, setContent를 안해서 아무것도 안나온 거였음...

        val message = intent.getStringExtra(EXTRA_MESSAGE).toString()

        view.sommernerName.setText(message)

//        Log.d("123", message)
    }
}