@file:Suppress("DEPRECATION")

package com.example.buildmovieapponline.Activites.DetailMovie

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.UI.stringForTime
import com.example.buildmovieapponline.databinding.ActivityDetailMovieBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.android.exoplayer2.ui.TimeBar


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

        // Bắt đầu cập nhật thời gian hiện tại từng giây
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

        // Lắng nghe sự kiện tua (scrubbing) trên TimeBar
        val exoTimeBar = findViewById<DefaultTimeBar>(R.id.exo_time_bar)
        exoTimeBar.addListener(object : TimeBar.OnScrubListener {
            override fun onScrubStart(timeBar: TimeBar, position: Long) {
                // Bắt đầu kéo tua
            }

            override fun onScrubMove(timeBar: TimeBar, position: Long) {
                // Cập nhật vị trí phát của video khi người dùng kéo tua
                player?.seekTo(position)
            }

            override fun onScrubStop(timeBar: TimeBar, position: Long, canceled: Boolean) {
                // Kết thúc tua
                player?.seekTo(position)
            }
        })
    }


    private val updateHandler = android.os.Handler(android.os.Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            // Cập nhật thời gian hiện tại của video lên TextView
            val timeExo = findViewById<TextView>(R.id.time_exo)
            val currentTime = player?.currentPosition ?: 0
            timeExo.text = stringForTime(currentTime)

            // Cập nhật lại sau mỗi 1 giây
            updateHandler.postDelayed(this, 1000)
        }
    }

    @SuppressLint("SetTextI18n")
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
        val settingButton = findViewById<ImageButton>(R.id.setting_exo)


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

        settingButton.setOnClickListener {
            showPlaybackSpeedMenu()
        }
    }

    private fun showPlaybackSpeedMenu() {
        val popupMenu = PopupMenu(this,findViewById(R.id.setting_exo))
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
        updateHandler.removeCallbacks(updateRunnable) // Hủy cập nhật khi thoát
        player?.release()
        player = null
    }

}
