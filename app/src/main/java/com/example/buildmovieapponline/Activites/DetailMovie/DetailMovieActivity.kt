package com.example.buildmovieapponline.Activites.DetailMovie

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.databinding.ActivityDetailMovieBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private var player: ExoPlayer? = null
    private var isFullscreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPlayer()
        displayMovieDetails()
        setupControlListeners()
        backArrowListeners()

    }

    private fun backArrowListeners() {
        val backExo = findViewById<ImageView>(R.id.back_exo)
        backExo.setOnClickListener{
            finish()
        }
    }

    private fun setupPlayer() {
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
        val videoUrl = "https://s4.phim1280.tv/20240922/4xwJfbDe/index.m3u8"
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = true
    }

    private fun displayMovieDetails() {
        binding.detailNameMovie.text = intent.getStringExtra("MOVIE_NAME")
        binding.detailCategoryMovie.text = "Thể loại: ${intent.getStringExtra("MOVIE_CATEGORY")}"
        binding.detailDurationMovie.text = "Thời lượng phim: ${intent.getIntExtra("MOVIE_DURATION", 0)} phút"
        binding.detailDescriptionMovie.text = intent.getStringExtra("MOVIE_DESCRIPTION")
    }

    private fun setupControlListeners() {
        val exoPlayButton = findViewById<ImageView>(R.id.exo_start)
        val exoRewindButton = findViewById<ImageView>(R.id.exo_rewind)
        val exoFastForwardButton = findViewById<ImageView>(R.id.exo_forward)
        val exoFullscreenButton = findViewById<ImageView>(R.id.exo_fullscreenn)

        exoPlayButton.setOnClickListener {
            if (player?.isPlaying == true) {
                player?.playWhenReady = false
                exoPlayButton.setImageResource(R.drawable.baseline_play_circle_outline_24)  // Icon phát khi video đang tạm dừng
            } else {
                player?.playWhenReady = true
                exoPlayButton.setImageResource(R.drawable.baseline_pause_circle_outline_24)  // Icon tạm dừng khi video đang phát
            }
        }

        exoRewindButton?.setOnClickListener {
            player?.seekTo(player?.currentPosition?.minus(10000) ?: 0)
        }

        exoFastForwardButton?.setOnClickListener {
            player?.seekTo(player?.currentPosition?.plus(10000) ?: 0)
        }

        exoFullscreenButton?.setOnClickListener {
            toggleFullscreen()
        }
    }

    private fun toggleFullscreen() {
        requestedOrientation = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
}
