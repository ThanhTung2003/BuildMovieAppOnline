package com.example.buildmovieapponline.Activites.DetailMovie

import MovieAdapter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buildmovieapponline.Model.RetrofitClient
import com.example.buildmovieapponline.ModelApi.ApiResponse
import com.example.buildmovieapponline.ModelApi.Category
import com.example.buildmovieapponline.ModelApi.Movie
import com.example.buildmovieapponline.ModelApi.MovieItemListener
import com.example.buildmovieapponline.databinding.ActivityDetailMovieBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private var player: ExoPlayer? = null
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPlayer()
        displayMovieDetails()
    }

    private fun displayMovieDetails() {
        binding.detailNameMovie.text = intent.getStringExtra("MOVIE_NAME")
        binding.detailCategoryMovie.text = "Thể loại: ${intent.getStringExtra("MOVIE_CATEGORY")}"
        binding.detailDurationMovie.text = "Thời lượng phim: ${intent.getIntExtra("MOVIE_DURATION", 0)} phút"
        binding.detailDescriptionMovie.text = intent.getStringExtra("MOVIE_DESCRIPTION")
    }

    private fun setupPlayer() {
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player

//        https://video.blender.org/download/videos/6402b77c-b61f-4a06-96ca-c8420a2becf4-1080.mp4
        val videoUrl = "https://s4.phim1280.tv/20240922/4xwJfbDe/index.m3u8"
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        player?.setMediaItem(mediaItem)

        player?.prepare()
        player?.playWhenReady = true
    }

    override fun onStart() {
        super.onStart()
        if (player == null) {
            setupPlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
}
