package com.example.buildmovieapponline.View_Activities.SearchActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buildmovieapponline.View_Activities.DetailMovie.DetailMovieActivity
import com.example.buildmovieapponline.Adapter.MovieAdapter.SearchAdapter
import com.example.buildmovieapponline.Model.RetrofitClient
import com.example.buildmovieapponline.Model.ApiResponse
import com.example.buildmovieapponline.Model.Movie
import com.example.buildmovieapponline.Model.MovieItemListener
import com.example.buildmovieapponline.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchEditText: EditText = findViewById(R.id.editTextSearch)
        val buttonBackSearch: ImageButton = findViewById(R.id.button_BackSearch)

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(v.text.toString())
                true
            } else false
        }

        buttonBackSearch.setOnClickListener{
            finish()
        }

    }

    private fun performSearch(query: String) {
        RetrofitClient.instance.getCategories().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val movies = ArrayList<Movie>()
                    // Lọc phim theo từ khóa tìm kiếm
                    response.body()?.body?.forEach { category ->
                        category.data.filter { movie ->
                            movie.name.contains(query, ignoreCase = true) // Phân biệt chữ hoa thường
                        }.also { filteredMovies ->
                            movies.addAll(filteredMovies)
                        }
                    }
                    updateUI(movies)
                } else {
                    Log.e("SearchActivity", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("SearchActivity", "Failure: ${t.message}")
            }
        })
    }

    private fun updateUI(movies: List<Movie>) {
        var categoryId = 1
        val adapter = SearchAdapter(movies,object : MovieItemListener{
            override fun onItemClick(movie: Movie) {
                val intent = Intent(this@SearchActivity, DetailMovieActivity::class.java)
                intent.putExtra("MOVIE_ID", movie.id)
                intent.putExtra("MOVIE_NAME", movie.name)
                intent.putExtra("CATEGORY_ID", categoryId)
                intent.putExtra("MOVIE_DURATION", movie.duration)
                intent.putExtra("MOVIE_DESCRIPTION", movie.description)
                startActivity(intent)
            }
        })
        findViewById<RecyclerView>(R.id.listSearchMovie).apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            this.adapter = adapter
        }
        if (movies.isEmpty()) {
            // Hiển thị thông báo không tìm thấy kết quả hoặc một số phản hồi phù hợp
            Toast.makeText(this@SearchActivity, "Không tìm thấy phim phù hợp", Toast.LENGTH_LONG).show()
        }
    }

}

