import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buildmovieapponline.Activites.DetailMovie.CategoryMovieActivity
import com.example.buildmovieapponline.Activites.MainActivity
import com.example.buildmovieapponline.ModelApi.Category
import com.example.buildmovieapponline.ModelApi.MovieItemListener
import com.example.buildmovieapponline.databinding.CategoryItemBinding

class CategoryAdapter(private val categories: List<Category>, private val listener: MainActivity) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)

        holder.binding.viewallCategory.setOnClickListener{
            val intent = Intent(holder.itemView.context,CategoryMovieActivity::class.java)
            intent.putExtra("CATEGORY_ID", category.category)
            intent.putExtra("CATEGORY_Title", category.title)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = categories.size

    class CategoryViewHolder(val binding: CategoryItemBinding, private val listener: MovieItemListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.titleCategory.text = category.title
            val movieAdapter = MovieAdapter(category.data, listener)
            binding.recyclerViewMovie.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerViewMovie.adapter = movieAdapter
        }
    }
}
