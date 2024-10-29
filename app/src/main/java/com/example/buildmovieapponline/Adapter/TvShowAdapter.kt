package com.example.buildmovieapponline.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buildmovieapponline.Model.DataTvShow.TvShow
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.databinding.TvshowItemBinding

class TvShowAdapter (
    private val tvShows: List<TvShow>,
    private val onItemClick: (TvShow) -> Unit
): RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    inner class TvShowViewHolder(private val binding: TvshowItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(tvShows: TvShow){
            binding.tvShowName.text = tvShows.name
            binding.tvShowOriginName.text = tvShows.origin_name
            binding.tvShowYear.text = tvShows.year.toString()
            binding.tvShowCountry.text = tvShows.country.joinToString { it.name }
            binding.tvShowEpisodeCurrent.text = tvShows.episode_current
            binding.tvShowTime.text = "Ngày cập nhật: ${tvShows.time}"

            Glide.with(binding.imageTvShow.context)
                .load("https://phimimg.com/${tvShows.poster_url}")
                .error(R.drawable.mytvcircle)
                .into(binding.imageTvShow)

            binding.root.setOnClickListener{onItemClick(tvShows)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding = TvshowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TvShowViewHolder(binding)
    }

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(tvShows[position])
    }
}

