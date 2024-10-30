package com.example.buildmovieapponline.View_Activities.DetailMovie

import com.example.buildmovieapponline.Adapter.MovieAdapter.CategoryMovieAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.buildmovieapponline.Model.DataXprogramer.RetrofitClient
import com.example.buildmovieapponline.Model.DataXprogramer.ApiResponse
import com.example.buildmovieapponline.Model.DataXprogramer.Movie
import com.example.buildmovieapponline.Model.DataXprogramer.MovieItemListener
import com.example.buildmovieapponline.databinding.ActivityCategoryMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMovieActivity : AppCompatActivity(), MovieItemListener {
    private lateinit var binding: ActivityCategoryMovieBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var categoryMovieAdapter: CategoryMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryId = intent.getIntExtra("CATEGORY_ID", 0)
        val categoryTitle = intent.getStringExtra("CATEGORY_Title") ?: "Unknown"
        binding.titleCategoryViewAll.text = categoryTitle

        progressBar = binding.progressBar2


        setupCategoryMovie()
        backArrow()
        loadMovies(categoryId)
    }

    private fun setupCategoryMovie() {
        categoryMovieAdapter = CategoryMovieAdapter(mutableListOf(), this)
        binding.RecyclerViewAllCategory.layoutManager = GridLayoutManager(this,2)
        binding.RecyclerViewAllCategory.adapter = categoryMovieAdapter
    }

    private fun loadMovies(categoryId: Int) {
        progressBar.visibility = View.VISIBLE
        RetrofitClient.instance.getCategories().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    // Tìm kiếm Category theo categoryId
                    val category = response.body()?.body?.find { it.category == categoryId }
                    if (category != null) {
                        // Lấy danh sách phim từ Category và cập nhật vào Adapter
                        categoryMovieAdapter.updateMovies(category.data)
                    } else {
                        Log.e("CategoryMovieActivity", "loi khong tim thay ID: $categoryId")
                    }
                } else {
                    Log.e("CategoryMovieActivity", "khong lay dc data category: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("CategoryMovieActivity", "loi fetching data: ${t.message}")
            }
        })
    }


    private fun backArrow() {
        binding.backViewAllCategory.setOnClickListener {
            finish()
        }
    }

    override fun onItemClick(movie: Movie) {
        var categoryId = 2
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra("MOVIE_ID", movie.id)
        intent.putExtra("MOVIE_DESCRIPTION", movie.description)
        intent.putExtra("MOVIE_DURATION", movie.duration)
        intent.putExtra("MOVIE_NAME", movie.name)
        intent.putExtra("CATEGORY_ID", categoryId)
        startActivity(intent)
    }
}
