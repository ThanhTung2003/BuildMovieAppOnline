package com.example.buildmovieapponline.Activites.DetailMovie

import CategoryMovieAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buildmovieapponline.Model.RetrofitClient
import com.example.buildmovieapponline.ModelApi.ApiResponse
import com.example.buildmovieapponline.ModelApi.Movie
import com.example.buildmovieapponline.databinding.ActivityCategoryMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMovieBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var categoryMovieAdapter: CategoryMovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryId = intent.getIntExtra("CATEGORY_ID", 0)
        val categoryTitle = intent.getStringExtra("CATEGORY_Title")
        binding.titleCategoryViewAll.text = categoryTitle

        backArrow()
        setupRecyclerViewCategory()
        loadMovies(categoryId)

    }

    private fun setupRecyclerViewCategory(){
        categoryMovieAdapter = CategoryMovieAdapter(mutableListOf(), this)
        binding.RecyclerViewAllCategory.apply {
            layoutManager = LinearLayoutManager(this@CategoryMovieActivity)
            adapter = categoryMovieAdapter
        }
    }


    private fun loadMovies(categoryId: Int) {
        RetrofitClient.instance.getMoviesByCategory(categoryId).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful && response.body()?.status == "success") {
                    categoryMovieAdapter.updateMovies(response.body()?.movies ?: emptyList())
                } else {
                    Log.e("CategoryMovieActivity", "Error fetching movies: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("CategoryMovieActivity", "API call failed: ${t.message}")
            }
        })
    }


    private fun backArrow() {
        binding.backViewAllCategory.setOnClickListener {
            finish()
        }
    }

    fun onItemClick(movie: Movie) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra("MOVIE_ID", movie.id)
        startActivity(intent)
    }

}