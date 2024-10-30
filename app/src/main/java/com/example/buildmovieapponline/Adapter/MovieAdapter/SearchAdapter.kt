package com.example.buildmovieapponline.Adapter.MovieAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buildmovieapponline.Model.Movie
import com.example.buildmovieapponline.Model.MovieItemListener
import com.example.buildmovieapponline.R

class SearchAdapter(private val movies: List<Movie>, private val listener: MovieItemListener) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener{
            listener.onItemClick(movies[position])
        }
    }

    override fun getItemCount(): Int = movies.size

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.search_NameMovie)
        private val durationTextView: TextView = itemView.findViewById(R.id.search_Duration)
        private val movieImageView: ImageView = itemView.findViewById(R.id.imageSearchMovie)

        fun bind(movie: Movie) {
            nameTextView.text = "Tên phim: ${movie.name}"
            durationTextView.text = "Thời lượng: ${movie.duration} phút"
            Glide.with(itemView.context).load(movie.logo).into(movieImageView)
        }
    }
}
