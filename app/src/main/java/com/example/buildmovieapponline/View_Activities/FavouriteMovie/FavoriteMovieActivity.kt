package com.example.buildmovieapponline.View_Activities.FavouriteMovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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