package com.example.buildmovieapponline.Activites.account

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.databinding.ActivityAccountBinding
import com.example.buildmovieapponline.databinding.ActivityDetailMovieBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backAccount()

    }

    private fun backAccount() {
        binding.backAccount.setOnClickListener {
            finish()
        }
    }
}