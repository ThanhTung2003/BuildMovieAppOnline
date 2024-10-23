package com.example.buildmovieapponline.Activites.TVChannel

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.databinding.ActivityAccountBinding
import com.example.buildmovieapponline.databinding.ActivityTvchannelBinding

class TVChannelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvchannelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvchannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backTV()

    }

    private fun backTV() {
        binding.backTvChanel.setOnClickListener {
            finish()
        }
    }
}