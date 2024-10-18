package com.example.buildmovieapponline.Activites.DetailMovie

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.UI.stringForTime
import com.example.buildmovieapponline.databinding.ActivityDetailMovieBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player


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
        val videoUrl = "https://s3.phim1280.tv/20240411/VjV5ECNY/index.m3u8"
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        player?.setMediaItem(mediaItem)
        player?.prepare()
//        player?.playWhenReady = true

        player!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == ExoPlayer.STATE_READY && player!!.playWhenReady) {
                    val exoDuration = findViewById<TextView>(R.id.exo_duration)
                    val duration = player!!.duration
                    exoDuration.setText(stringForTime(duration))
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (!isPlaying) {
                    val timeExo = findViewById<TextView>(R.id.time_exo)
                    val position = player!!.currentPosition
                    timeExo.setText(stringForTime(position))
                }
            }
        })

    }

    private fun displayMovieDetails() {
        binding.detailNameMovie.text = intent.getStringExtra("MOVIE_NAME")
        binding.detailCategoryMovie.text = "Thể loại: ${intent.getStringExtra("MOVIE_CATEGORY")}"
        binding.detailDurationMovie.text = "Thời lượng phim: ${intent.getIntExtra("MOVIE_DURATION", 0)} phút"
        binding.detailDescriptionMovie.text = intent.getStringExtra("MOVIE_DESCRIPTION")
        val titleExo = findViewById<TextView>(R.id.title_exo)
        titleExo.text = intent.getStringExtra("MOVIE_NAME")
    }

    private fun setupControlListeners() {
        val exoPlayButton = findViewById<ImageView>(R.id.exo_start)
        val exoRewindButton = findViewById<ImageView>(R.id.exo_rewind)
        val exoFastForwardButton = findViewById<ImageView>(R.id.exo_forward)
        val exoFullscreenButton = findViewById<ImageView>(R.id.exo_fullscreenn)

        exoPlayButton.setOnClickListener {
            togglePlayPause()
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

    private fun togglePlayPause() {
        if (player?.isPlaying == true)
        {
            player?.playWhenReady = false
            findViewById<ImageView>(R.id.exo_start).setImageResource(R.drawable.baseline_play_circle_outline_24)
        }else{
            player?.playWhenReady = true
            findViewById<ImageView>(R.id.exo_start).setImageResource(R.drawable.baseline_pause_circle_outline_24)
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
