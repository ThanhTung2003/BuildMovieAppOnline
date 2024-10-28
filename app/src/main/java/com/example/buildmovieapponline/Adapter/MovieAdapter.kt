import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buildmovieapponline.Model.Movie
import com.example.buildmovieapponline.Model.MovieItemListener
import com.example.buildmovieapponline.databinding.MovieItemBinding

class MovieAdapter(private val movies: List<Movie>, private val listener: MovieItemListener) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.itemView.setOnClickListener {
            listener.onItemClick(movie)
        }
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.titleMovie.text = movie.name
            Glide.with(itemView.context)
                .load(movie.logo)
                .into(binding.imageMovie)
        }
    }
}

