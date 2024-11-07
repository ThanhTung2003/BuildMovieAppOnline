package com.example.buildmovieapponline.View_Activities.SearchActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buildmovieapponline.View_Activities.DetailMovie.DetailMovieActivity
import com.example.buildmovieapponline.Adapter.MovieAdapter.SearchAdapter
import com.example.buildmovieapponline.Const.CompanionObject.Companion.CATEGORY_ID
import com.example.buildmovieapponline.Const.CompanionObject.Companion.MOVIE_DESCRIPTION
import com.example.buildmovieapponline.Const.CompanionObject.Companion.MOVIE_DURATION
import com.example.buildmovieapponline.Const.CompanionObject.Companion.MOVIE_ID
import com.example.buildmovieapponline.Const.CompanionObject.Companion.MOVIE_NAME
import com.example.buildmovieapponline.Model.DataXprogramer.RetrofitClient
import com.example.buildmovieapponline.Model.DataXprogramer.ApiResponse
import com.example.buildmovieapponline.Model.DataXprogramer.Movie
import com.example.buildmovieapponline.Model.DataXprogramer.MovieItemListener
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
        val progressBarSearch = findViewById<ProgressBar>(R.id.progressBarSearch)
        progressBarSearch.visibility = View.VISIBLE

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
                progressBarSearch.visibility =View.GONE
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("SearchActivity", "Failure: ${t.message}")
                progressBarSearch.visibility =View.GONE
            }
        })
    }

    private fun updateUI(movies: List<Movie>) {
        var categoryId = 1
        val adapter = SearchAdapter(movies,object : MovieItemListener {
            override fun onItemClick(movie: Movie) {
                val intent = Intent(this@SearchActivity, DetailMovieActivity::class.java)
                intent.putExtra(MOVIE_ID, movie.id)
                intent.putExtra(MOVIE_NAME, movie.name)
                intent.putExtra(CATEGORY_ID, categoryId)
                intent.putExtra(MOVIE_DURATION, movie.duration)
                intent.putExtra(MOVIE_DESCRIPTION, movie.description)
                startActivity(intent)
            }
        })
        findViewById<RecyclerView>(R.id.listSearchMovie).apply {
            layoutManager = GridLayoutManager(this@SearchActivity,2)
            this.adapter = adapter
        }
        // search_results
        val searchResultsTextView: TextView = findViewById(R.id.search_results)
        if (movies.isEmpty()) {
            searchResultsTextView.text = "${movies.size} Kết quả tìm kiếm"
            Toast.makeText(this@SearchActivity, "Không tìm thấy phim phù hợp", Toast.LENGTH_LONG).show()
        }else{
            searchResultsTextView.text = "${movies.size} Kết quả tìm kiếm"
        }
    }

}

