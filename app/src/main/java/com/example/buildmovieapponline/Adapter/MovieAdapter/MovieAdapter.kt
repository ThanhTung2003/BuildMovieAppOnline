package com.example.buildmovieapponline.Adapter.MovieAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buildmovieapponline.Model.DataXprogramer.Movie
import com.example.buildmovieapponline.Model.DataXprogramer.MovieItemListener
import com.example.buildmovieapponline.databinding.Movie2ItemBinding
import com.example.buildmovieapponline.databinding.Movie3ItemBinding
import com.example.buildmovieapponline.databinding.MovieItemBinding

class MovieAdapter(
    private val movies: List<Movie>,
    private val listener: MovieItemListener,
    private val categoryId: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Define view types for each layout
    companion object {
        const val VIEW_TYPE_1 = 1
        const val VIEW_TYPE_2 = 2
        const val VIEW_TYPE_3 = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (categoryId) {
            1 -> VIEW_TYPE_1
            2 -> VIEW_TYPE_2
            3 -> VIEW_TYPE_3
            else -> VIEW_TYPE_1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_1 -> {
                val binding = MovieItemBinding.inflate(inflater, parent, false)
                MovieViewHolderType1(binding)
            }
            VIEW_TYPE_2 -> {
                val binding = Movie2ItemBinding.inflate(inflater, parent, false)
                MovieViewHolderType2(binding)
            }
            VIEW_TYPE_3 -> {
                val binding = Movie3ItemBinding.inflate(inflater, parent, false)
                MovieViewHolderType3(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movies[position]
        when (holder) {
            is MovieViewHolderType1 -> holder.bind(movie, listener)
            is MovieViewHolderType2 -> holder.bind(movie, listener)
            is MovieViewHolderType3 -> holder.bind(movie, listener)
        }
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolderType1(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, listener: MovieItemListener) {
            binding.titleMovie.text = movie.name
            Glide.with(itemView.context).load(movie.logo).into(binding.imageMovie)
            itemView.setOnClickListener { listener.onItemClick(movie) }
        }
    }

    class MovieViewHolderType2(private val binding: Movie2ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, listener: MovieItemListener) {
            Glide.with(itemView.context).load(movie.logo).into(binding.imageMovie2)
            itemView.setOnClickListener { listener.onItemClick(movie) }
        }
    }

    class MovieViewHolderType3(private val binding: Movie3ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, listener: MovieItemListener) {
            Glide.with(itemView.context).load(movie.logo).into(binding.imageMovie2)
            itemView.setOnClickListener { listener.onItemClick(movie) }
        }
    }
}


