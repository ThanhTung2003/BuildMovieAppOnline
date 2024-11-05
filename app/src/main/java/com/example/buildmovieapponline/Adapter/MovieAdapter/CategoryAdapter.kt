package com.example.buildmovieapponline.Adapter.MovieAdapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buildmovieapponline.Const.CompanionObject.Companion.CATEGORY_ID
import com.example.buildmovieapponline.Const.CompanionObject.Companion.CATEGORY_TITLE
import com.example.buildmovieapponline.View_Activities.DetailMovie.CategoryMovieActivity
import com.example.buildmovieapponline.View_Activities.MainActivity
import com.example.buildmovieapponline.Model.DataXprogramer.Category
import com.example.buildmovieapponline.Model.DataXprogramer.MovieItemListener
import com.example.buildmovieapponline.databinding.CategoryItemBinding

class CategoryAdapter(
    private val categories: List<Category>,
    private val listener: MovieItemListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    class CategoryViewHolder(
        val binding: CategoryItemBinding,
        private val listener: MovieItemListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.titleCategory.text = category.title

            val movieAdapter = MovieAdapter(category.data, listener, category.category,)
            binding.recyclerViewMovie.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerViewMovie.adapter = movieAdapter

            binding.viewallCategory.setOnClickListener {
                val intent = Intent(itemView.context, CategoryMovieActivity::class.java).apply {
                    putExtra(CATEGORY_ID, category.category)
                    putExtra(CATEGORY_TITLE, category.title)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}

