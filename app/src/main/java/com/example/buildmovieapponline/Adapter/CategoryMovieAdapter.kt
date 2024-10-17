import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buildmovieapponline.Activites.DetailMovie.CategoryMovieActivity
import com.example.buildmovieapponline.ModelApi.Movie
import com.example.buildmovieapponline.databinding.MovieItemBinding

class CategoryMovieAdapter(private var movies: MutableList<Movie>, private val listener: CategoryMovieActivity) : RecyclerView.Adapter<CategoryMovieAdapter.MovieViewHolder>() {

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
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.titleMovie.text = movie.name
            Glide.with(itemView.context)
                .load(movie.logo)
                .into(binding.imageMovie)
        }
    }
}
