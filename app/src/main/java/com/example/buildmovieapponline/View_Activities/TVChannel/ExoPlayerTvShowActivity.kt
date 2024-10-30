@file:Suppress("DEPRECATION")

package com.example.buildmovieapponline.View_Activities.TVChannel

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.UI.stringForTime
import com.example.buildmovieapponline.databinding.ActivityExoPlayerTvShowBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class ExoPlayerTvShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExoPlayerTvShowBinding
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExoPlayerTvShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exotitle = findViewById<TextView>(R.id.title_exo)
        val movieName = intent.getStringExtra("MOVIE_NAME")
        exotitle.text = movieName

        val videoUrl = intent.getStringExtra("VIDEO_URL") ?: ""
        setupPlayer(videoUrl) // Khởi tạo player với video URL
        setupControlListeners()
        backArrowListeners()
    }

    private fun setupPlayer(videoUrl: String) {
        player = ExoPlayer.Builder(this).build()
        binding.exoplayerTVSHOW.player = player

        binding.progressBarExoTVShow.visibility = View.GONE
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = false

        // Bắt đầu cập nhật thời gian hiện tại
        updateHandler.post(updateRunnable)

        player!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == ExoPlayer.STATE_READY && player!!.playWhenReady) {
                    val exoDuration = findViewById<TextView>(R.id.exo_duration)
                    val duration = player!!.duration
                    exoDuration.text = stringForTime(duration)
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (!isPlaying) {
                    val timeExo = findViewById<TextView>(R.id.time_exo)
                    val position = player!!.currentPosition
                    timeExo.text = stringForTime(position)
                }
            }
        })
    }

    private val updateHandler = android.os.Handler(android.os.Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            val timeExo = findViewById<TextView>(R.id.time_exo)
            val currentTime = player?.currentPosition ?: 0
            timeExo.text = stringForTime(currentTime)

            // Cập nhật lại sau mỗi 1 giây
            updateHandler.postDelayed(this, 1000)
        }
    }

    private fun backArrowListeners() {
        val backExo = findViewById<ImageView>(R.id.back_exo)
        backExo.setOnClickListener {
            finish()
        }
    }

    private fun setupControlListeners() {
        val exoPlayButton = findViewById<ImageView>(R.id.exo_start)
        val exoRewindButton = findViewById<ImageView>(R.id.exo_rewind)
        val exoFastForwardButton = findViewById<ImageView>(R.id.exo_forward)
        val exoFullscreenButton = findViewById<ImageView>(R.id.exo_fullscreenn)
        val settingButton = findViewById<ImageButton>(R.id.setting_exo)

        exoPlayButton.setOnClickListener {
            togglePlayPause()
        }

        exoRewindButton.setOnClickListener {
            player?.seekTo((player?.currentPosition ?: 0) - 10000)
        }

        exoFastForwardButton.setOnClickListener {
            player?.seekTo((player?.currentPosition ?: 0) + 10000)
        }

        exoFullscreenButton.setOnClickListener {
            toggleFullscreen()
        }

        settingButton.setOnClickListener {
            showPlaybackSpeedMenu()
        }
    }

    private fun showPlaybackSpeedMenu() {
        val popupMenu = PopupMenu(this, findViewById(R.id.setting_exo))
        popupMenu.menuInflater.inflate(R.menu.playback_speed_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.speed_0_25x -> player?.setPlaybackSpeed(0.25f)
                R.id.speed_0_5x -> player?.setPlaybackSpeed(0.5f)
                R.id.speed_1x -> player?.setPlaybackSpeed(1.0f)
                R.id.speed_1_5x -> player?.setPlaybackSpeed(1.5f)
                R.id.speed_2x -> player?.setPlaybackSpeed(2.0f)
            }
            true
        }
        popupMenu.show()
    }

    private fun togglePlayPause() {
        if (player?.isPlaying == true) {
            player?.playWhenReady = false
            findViewById<ImageView>(R.id.exo_start).setImageResource(R.drawable.baseline_play_circle_outline_24)
        } else {
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
        updateHandler.removeCallbacks(updateRunnable)
        player?.release()
        player = null
    }
}

