package com.example.buildmovieapponline.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.databinding.EpisodesItemBinding

class EpisodeAdapter(
    private val episodeNames: List<String>,
    private val onEpisodeClick: (String) -> Unit
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    inner class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = EpisodesItemBinding.bind(view)

        fun bind(episodeName: String) {
            binding.buttonEpisodes.text = episodeName
            binding.buttonEpisodes.setOnClickListener {
                onEpisodeClick(episodeName) // khi nhấn vào tập
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.episodes_item, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodeNames[position])
    }

    override fun getItemCount(): Int = episodeNames.size
}
