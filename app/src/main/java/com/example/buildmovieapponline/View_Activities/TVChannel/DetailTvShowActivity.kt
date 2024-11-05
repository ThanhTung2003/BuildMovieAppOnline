package com.example.buildmovieapponline.View_Activities.TVChannel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.buildmovieapponline.Adapter.EpisodeAdapter
import com.example.buildmovieapponline.Const.CompanionObject.Companion.DETAIL_TVSHOWACTIVITY
import com.example.buildmovieapponline.Const.CompanionObject.Companion.MOVIE_NAME
import com.example.buildmovieapponline.Const.CompanionObject.Companion.VIDEO_URL
import com.example.buildmovieapponline.Model.DataTvShow.MovieDetail.TvShowDetailResponse
import com.example.buildmovieapponline.Model.DataTvShow.TvShowRetrofitClient
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.databinding.ActivityDetailTvShowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.buildmovieapponline.Model.DataTvShow.MovieDetail.MovieDetails


class DetailTvShowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTvShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvShowSlug = intent.getStringExtra("TV_SHOW_SLUG") ?:return

        fetchTvShowDetails(tvShowSlug)
        backDetailTvShow()

    }

    private fun backDetailTvShow() {
        binding.backDetailTvShow.setOnClickListener {
            finish()
        }
    }

    private fun fetchTvShowDetails(slug: String) {
        val url = "https://phimapi.com/phim/$slug"
        TvShowRetrofitClient.instance.getTvShowDetails(url).enqueue(object :
            Callback<TvShowDetailResponse> {
            override fun onResponse(call: Call<TvShowDetailResponse>, response: Response<TvShowDetailResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val tvShowDetails = response.body()!!.movie
                    displayTvShowDetails(tvShowDetails)

                    // Map name và link_m3u8 của tập phim
                    val episodeNames = response.body()!!.episodes.flatMap { episode ->
                        episode.server_data.map { data -> data.name to data.link_m3u8 }
                    }
                    setupEpisodesRecyclerView(episodeNames, tvShowDetails.name)
                } else {
                    Log.e(DETAIL_TVSHOWACTIVITY, "Không thể tải chi tiết của TV Show")
                }
                binding.progressBarDetailTVShow.visibility = View.GONE
            }

            override fun onFailure(call: Call<TvShowDetailResponse>, t: Throwable) {
                Log.e(DETAIL_TVSHOWACTIVITY, "Lỗi: ${t.message}")
            }
        })
    }


    @SuppressLint("SetTextI18n")
    private fun displayTvShowDetails(tvShowDetails: MovieDetails) {

        binding.detailTvShowName.text = tvShowDetails.name
        binding.detailTvShowOriginName.text = tvShowDetails.origin_name
        binding.detailTvShowYear.text = "Năm phát hành: ${tvShowDetails.year}"
        binding.detailTvShowCountry.text = "Quốc gia: ${tvShowDetails.country.joinToString { it.name }}"
        binding.detailTvShowEpisodeTotal.text = "Số tập: ${tvShowDetails.episode_total}"
        binding.detailTvShowTime.text = "Thời lượng: ${tvShowDetails.time}"
        binding.detailTvShowQuality.text = "Chất lượng: ${tvShowDetails.quality}"
        binding.detailTvShowDirector.text = "Đạo diễn: ${tvShowDetails.director.joinToString()}"
        binding.detailTvShowActor.text = "Diễn viên: ${tvShowDetails.actor.joinToString()}"
        binding.detailTvShowCategory.text = "Thể loại: ${tvShowDetails.category.joinToString { it.name }}"
        binding.detailTvShowContent.text = "Mô tả: ${tvShowDetails.content}"

        Glide.with(this)
            .load(tvShowDetails.thumb_url)
            .error(R.drawable.mytvcircle)
            .into(binding.detailPosterTvShow)
    }

    private fun setupEpisodesRecyclerView(episodeNames: List<Pair<String, String>>, movieName:String) {
        val episodeAdapter = EpisodeAdapter(episodeNames) { m3u8Link ->
            val intent = Intent(this, ExoPlayerTvShowActivity::class.java)
            intent.putExtra(MOVIE_NAME, movieName )
            intent.putExtra(VIDEO_URL, m3u8Link) // Truyền link m3u8 vào ExoPlayerTvShowActivity
            startActivity(intent)
        }
        binding.recyclerViewEpisodes.apply {
            layoutManager = GridLayoutManager(this@DetailTvShowActivity, 4)
            adapter = episodeAdapter
        }
    }


}