package com.example.buildmovieapponline.Adapter.TvShowAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buildmovieapponline.Model.DataTvShow.TvShow
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.databinding.TvshowItemBinding

class TvShowAdapter(
    private val tvShows: List<TvShow>,
    private val onItemClick: (TvShow) -> Unit
) : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    inner class TvShowViewHolder(private val binding: TvshowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(tvShow: TvShow) {
            binding.tvShowName.text = tvShow.name
            binding.tvShowOriginName.text = tvShow.origin_name
            binding.tvShowYear.text = "Năm: ${tvShow.year}"
            binding.tvShowCountry.text = "Quốc gia: ${tvShow.country.joinToString { it.name }}"
            binding.tvShowEpisodeCurrent.text = "Tình trạng: ${tvShow.episode_current}"
            binding.tvShowTime.text = "Thời lượng: ${tvShow.time}"

            Glide.with(binding.imageTvShow.context)
                .load("https://phimimg.com/${tvShow.poster_url}")
                .error(R.drawable.mytvcircle)
                .into(binding.imageTvShow)

            binding.root.setOnClickListener {
                onItemClick(tvShow)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding = TvshowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(tvShows[position])
    }
}


