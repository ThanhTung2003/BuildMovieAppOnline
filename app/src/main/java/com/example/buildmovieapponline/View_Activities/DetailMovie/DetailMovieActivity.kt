@file:Suppress("DEPRECATION")

package com.example.buildmovieapponline.View_Activities.DetailMovie

import com.example.buildmovieapponline.Adapter.MovieAdapter.CategoryMovieAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buildmovieapponline.Model.DataXprogramer.ApiResponse
import com.example.buildmovieapponline.Model.DataXprogramer.RetrofitClient
import com.example.buildmovieapponline.Model.DataVideo.VideoResponse
import com.example.buildmovieapponline.Model.DataXprogramer.Movie
import com.example.buildmovieapponline.Model.DataXprogramer.MovieItemListener
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.UI.stringForTime
import com.example.buildmovieapponline.databinding.ActivityDetailMovieBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailMovieActivity : AppCompatActivity(), MovieItemListener {
    private lateinit var binding: ActivityDetailMovieBinding
    private var player: ExoPlayer? = null
    private var isFullscreen = false
    private var isDescriptionExpanded = false
    private lateinit var categoryMovieAdapter: CategoryMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getStringExtra("MOVIE_ID") ?: ""
        displayMovieDetails()

        if (movieId.isNotEmpty()) {
            fetchVideoLink(movieId) // Gọi API để lấy link video theo id
        } else {
            Toast.makeText(this, "Movie ID không hợp lệ", Toast.LENGTH_SHORT).show()
        }

        val categoryId = intent.getIntExtra("CATEGORY_ID", 0)
        if (categoryId != 0) {
            setSameCategoryMovie()
            loadSameCategoryMovies(categoryId)
        }

        detailMovieShowMore()
        displayMovieDetails()
        setupControlListeners()
        backArrowListeners()

    }

    private fun detailMovieShowMore() {
//        binding.detailDescriptionMovie.post{
//            if (binding.detailDescriptionMovie.lineCount > 3){
//                binding.showMoreButton.visibility = View.VISIBLE
//                binding.detailDescriptionMovie.maxLines = 3
//                binding.showMoreButton.ellipsize = TextUtils.TruncateAt.END
//            }
//        }
//        binding.showMoreButton.setOnClickListener {
//            isDescriptionExpanded = !isDescriptionExpanded
//            if (isDescriptionExpanded) {
//                binding.detailDescriptionMovie.maxLines = Int.MAX_VALUE
//                binding.detailDescriptionMovie.ellipsize = null
//                binding.showMoreButton.text = "Thu gọn"
//            } else {
//                binding.detailDescriptionMovie.maxLines = 3
//                binding.detailDescriptionMovie.ellipsize = TextUtils.TruncateAt.END
//                binding.showMoreButton.text = "Xem thêm"
//            }
//        }
    }

    private fun loadSameCategoryMovies(categoryId: Int) {
        binding.progressBarSameCategoryMovie.visibility = View.VISIBLE
        RetrofitClient.instance.getCategories().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                binding.progressBarSameCategoryMovie.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    val categoryData = response.body()?.body?.find { it.category == categoryId }
                    categoryData?.let {
                        categoryMovieAdapter.updateMovies(it.data) // Cập nhật danh sách phim
                    }
                } else {
                    Log.e("DetailMovieActivity", "Không thể tải danh sách phim cùng thể loại")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                binding.progressBarSameCategoryMovie.visibility = View.GONE
                Log.e("DetailMovieActivity", "Lỗi khi tải dữ liệu: ${t.message}")
            }
        })
    }

    private fun setSameCategoryMovie() {
        categoryMovieAdapter = CategoryMovieAdapter(mutableListOf(), this)
        binding.detailRecyclerViewSameMovie.apply {
            layoutManager = LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryMovieAdapter
        }
    }


    private fun fetchVideoLink(movieId: String) {
        RetrofitClient.instance.getVideoLink(movieId).enqueue(object : Callback<VideoResponse> {
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                if (response.isSuccessful && response.body()?.status == "success") {
                    val videoUrl = response.body()?.body?.url // Lấy link video từ đối tượng body
                    setupPlayer(videoUrl ?: "") // Gọi hàm setupPlayer với link video
                } else {
                    Toast.makeText(this@DetailMovieActivity, "Không tìm thấy link video", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                Log.e("check", "Lỗi khi lấy link video: ${t.message}")
                Toast.makeText(this@DetailMovieActivity, "Lỗi khi lấy link video", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun backArrowListeners() {
        val backExo = findViewById<ImageView>(R.id.back_exo)
        backExo.setOnClickListener{
            finish()
        }
    }

    private fun setupPlayer(videoUrl: String) {
        if (videoUrl.isNotEmpty()) {
            player = ExoPlayer.Builder(this).build()
            binding.playerView.player = player

            val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
            player?.setMediaItem(mediaItem)
            player?.prepare()
            player?.playWhenReady = false
        } else {
            Toast.makeText(this, "Link video không hợp lệ", Toast.LENGTH_SHORT).show()
        }

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

    override fun onItemClick(movie: Movie) {
        var categoryId = 2
        val currentMovieId = intent.getStringExtra("MOVIE_ID") ?: ""
        if (movie.id != currentMovieId) {
            val intent = Intent(this, DetailMovieActivity::class.java)
            intent.putExtra("MOVIE_ID", movie.id)
            intent.putExtra("MOVIE_DESCRIPTION", movie.description)
            intent.putExtra("MOVIE_DURATION", movie.duration)
            intent.putExtra("CATEGORY_ID", categoryId)
            intent.putExtra("MOVIE_CATEGORY", movie.category)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Đây là phim hiện tại", Toast.LENGTH_SHORT).show()
        }
    }
}
