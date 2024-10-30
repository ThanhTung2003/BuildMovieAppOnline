package com.example.buildmovieapponline.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.databinding.EpisodesItemBinding

class EpisodeAdapter(
    private val episodes: List<Pair<String, String>>,
    private val onEpisodeClick: (String) -> Unit
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    inner class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = EpisodesItemBinding.bind(view)

        fun bind(episode: Pair<String, String>) {
            val (episodeName, m3u8Link) = episode
            binding.buttonEpisodes.text = episodeName
            binding.buttonEpisodes.setOnClickListener {
                onEpisodeClick(m3u8Link) // Khi nhấn vào tập, truyền link m3u8
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.episodes_item, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount(): Int = episodes.size
}
