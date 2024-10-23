package com.example.buildmovieapponline.Activites.FavouriteMovie

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.databinding.ActivityFavoriteMovieBinding

class FavoriteMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteMovieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backFavouriteMovie()
    }

    private fun backFavouriteMovie() {
        binding.backFavouriteMovie.setOnClickListener {
            finish()
        }
    }
}