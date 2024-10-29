package com.example.buildmovieapponline.View_Activities

import CategoryAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buildmovieapponline.View_Activities.DetailMovie.DetailMovieActivity
import com.example.buildmovieapponline.View_Activities.FavouriteMovie.FavoriteMovieActivity
import com.example.buildmovieapponline.View_Activities.SearchActivity.SearchActivity
import com.example.buildmovieapponline.View_Activities.TVChannel.TVChannelActivity
import com.example.buildmovieapponline.Domain.SliderItems
import com.example.buildmovieapponline.Adapter.SliderAdapter
import com.example.buildmovieapponline.Model.RetrofitClient
import com.example.buildmovieapponline.Model.ApiResponse
import com.example.buildmovieapponline.Model.Category
import com.example.buildmovieapponline.Model.Movie
import com.example.buildmovieapponline.Model.MovieItemListener
import com.example.buildmovieapponline.ViewModel.MainViewModel
import com.example.buildmovieapponline.View_Activities.account.AccountActivity
import com.example.buildmovieapponline.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),MovieItemListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sliderAdapter: SliderAdapter
    private var sliderItems: MutableList<SliderItems> = ArrayList()
    private lateinit var progressBar: ProgressBar
    private var currentPage = 0

    private val sliderHandler : Handler = Handler(Looper.getMainLooper())
    private lateinit var sliderRunnable:Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        progressBar = binding.progressBar1

        initView()
        banners()
        setupSliderRunnable()
        buttonBottomappbar()

    }

    private fun buttonBottomappbar() {
        //trang chủ
        binding.buttonHomepage.setOnClickListener {
            val intent = Intent(this@MainActivity,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            finish()
        }
        binding.iconlogohome.setOnClickListener {
            val intent = Intent(this@MainActivity,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            finish()
        }
        //tv chanel
        binding.iconTvChanel.setOnClickListener{
            val intent = Intent(this@MainActivity, TVChannelActivity::class.java)
            startActivity(intent)
        }
        //Favourite Movie
        binding.iconFavouriteMovie.setOnClickListener{
            val intent = Intent(this@MainActivity, FavoriteMovieActivity::class.java)
            startActivity(intent)
        }
        //account
        binding.iconUser.setOnClickListener{
            val intent = Intent(this@MainActivity, AccountActivity::class.java)
            startActivity(intent)
        }

        binding.iconAccount.setOnClickListener {
            val intent = Intent(this@MainActivity, AccountActivity::class.java)
            startActivity(intent)
        }
        //search
        binding.iconSearch.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            intent.putExtra("search_query", binding.iconSearch.textAlignment.toString())
            startActivity(intent)
        }
    }

    private fun banners() {
        sliderItems.apply {
            sliderItems.add(SliderItems("https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2022/9/6/1089731/03_TIEU-VY-01.jpg"))
            sliderItems.add(SliderItems("https://vntravel.org.vn/uploads/images/2024/02/20/poster-mai-scaled-1708403724.jpg"))
            sliderItems.add(SliderItems("https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/176627/Originals/poster-phim-hoat-hinh-1.jpg"))
            sliderItems.add(SliderItems("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSU5x4RG6kKHVlboTSZbUoB6wTfeuHPI3t-8FT3po3Q18sxrcco9j4J4bJBFZtjqZ6e27k&usqp=CAU"))
            sliderItems.add(SliderItems("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ0nzWfFNWdZKYeWV-I8Lm1u9BDUfGI0ls3Aw&s"))
        }

        sliderAdapter = SliderAdapter(binding.viewpagerSlider, sliderItems)
        binding.viewpagerSlider.adapter = sliderAdapter

    }

    private fun setupSliderRunnable() {
        sliderRunnable = Runnable() {
            sliderRunnable = Runnable {
                currentPage = (currentPage + 1) % sliderAdapter.itemCount
                binding.viewpagerSlider.currentItem = currentPage
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
            startAutoSlider()
        }
    }

    private fun startAutoSlider() {
        sliderHandler.postDelayed(sliderRunnable, 3000)  // Bắt đầu chạy tự động
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)  // Ngừng khi Activity không hiển thị
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)  // Tiếp tục khi Activity hiển thị lại
    }

    private fun initView() {
        progressBar.visibility = View.VISIBLE  // Hiển thị ProgressBar khi bắt đầu tải dữ liệu
        RetrofitClient.instance.getCategories().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                progressBar.visibility = View.GONE  // Ẩn ProgressBar khi nhận được phản hồi
                if (response.isSuccessful) {
                    val categories = response.body()?.body ?: emptyList()
                    setupCategories(categories)
                } else {
                    Log.e("MainActivity", "Error fetching categories: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                progressBar.visibility = View.GONE  // Ẩn ProgressBar khi có lỗi
                Log.e("MainActivity", "API call failed: ${t.message}")
            }
        })
    }

    // chuyển sang detail phim
    override fun onItemClick(movie: Movie) {
        var categoryId: Int = 1
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra("MOVIE_ID", movie.id)
        intent.putExtra("MOVIE_NAME", movie.name)
        intent.putExtra("CATEGORY_ID", categoryId)
        intent.putExtra("MOVIE_DURATION", movie.duration)
        intent.putExtra("MOVIE_DESCRIPTION", movie.description)
        startActivity(intent)
    }

    private fun setupCategories(categories: List<Category>) {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewBestMovie.layoutManager = layoutManager
        binding.recyclerViewBestMovie.adapter = CategoryAdapter(categories, this)
    }

}
