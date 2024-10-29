package com.example.buildmovieapponline.View_Activities.TVChannel

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buildmovieapponline.Adapter.TvShowAdapter
import com.example.buildmovieapponline.Model.DataTvShow.TvShow
import com.example.buildmovieapponline.Model.DataTvShow.TvShowResponse
import com.example.buildmovieapponline.Model.DataTvShow.TvShowRetrofitClient
import com.example.buildmovieapponline.databinding.ActivityTvchannelBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVChannelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvchannelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvchannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backTV()
        loadTvShows()

    }

    private fun loadTvShows() {
        binding.progressBarTVShow.visibility = View.VISIBLE

        TvShowRetrofitClient.instance.getTvShow().enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                binding.progressBarTVShow.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    val tvShows = response.body()!!.data.items
                    setupRecyclerView(tvShows)
                } else {
                    Log.e("TVChannelActivity", "Failed to fetch TV shows")
                }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                binding.progressBarTVShow.visibility = View.GONE
                Log.e("TVChannelActivity", "Error: ${t.message}")
            }
        })
    }

    private fun setupRecyclerView(tvShows: List<TvShow>){
        binding.recyclerViewTVShow.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.recyclerViewTVShow.adapter = TvShowAdapter(tvShows) {tvShows -> this}
    }

    private fun backTV() {
        binding.backTvChanel.setOnClickListener {
            finish()
        }
    }
}