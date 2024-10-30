package com.example.buildmovieapponline.Adapter.MovieAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buildmovieapponline.Model.DataXprogramer.Movie
import com.example.buildmovieapponline.Model.DataXprogramer.MovieDiffCallback
import com.example.buildmovieapponline.Model.DataXprogramer.MovieItemListener
import com.example.buildmovieapponline.R
import com.example.buildmovieapponline.databinding.MovieItemBinding

class CategoryMovieAdapter(
    private var movies: MutableList<Movie>,
    private val listener: MovieItemListener
) : RecyclerView.Adapter<CategoryMovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.itemView.setOnClickListener {
            listener.onItemClick(movie)
        }
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        val diffCallback = MovieDiffCallback(movies, newMovies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        movies.clear()
        movies.addAll(newMovies)
        diffResult.dispatchUpdatesTo(this)
    }

    class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.titleMovie.text = movie.name
            Glide.with(itemView.context)
                .load(movie.logo)
                .placeholder(R.drawable.mytvcircle) // Hình ảnh chờ
                .error(R.drawable.mytvcircle) // Hình ảnh lỗi
                .into(binding.imageMovie)
        }
    }
}
