package com.example.buildmovieapponline.View_Activities.TVChannel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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