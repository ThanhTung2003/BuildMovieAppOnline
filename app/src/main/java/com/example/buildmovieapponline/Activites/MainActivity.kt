package com.example.buildmovieapponline.Activites

import CategoryAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.buildmovieapponline.Domain.SliderItems
import com.example.buildmovieapponline.Adapter.SliderAdapter
import com.example.buildmovieapponline.Model.RetrofitClient
import com.example.buildmovieapponline.ModelApi.ApiResponse
import com.example.buildmovieapponline.ModelApi.Category
import com.example.buildmovieapponline.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sliderAdapter: SliderAdapter
    private var sliderItems: MutableList<SliderItems> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        banners()
    }

    private fun banners() {
        // Thêm các URL hình ảnh
        sliderItems.add(SliderItems("https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2022/9/6/1089731/03_TIEU-VY-01.jpg"))
        sliderItems.add(SliderItems("https://vntravel.org.vn/uploads/images/2024/02/20/poster-mai-scaled-1708403724.jpg"))
        sliderItems.add(SliderItems("https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/176627/Originals/poster-phim-hoat-hinh-1.jpg"))
        sliderItems.add(SliderItems("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSU5x4RG6kKHVlboTSZbUoB6wTfeuHPI3t-8FT3po3Q18sxrcco9j4J4bJBFZtjqZ6e27k&usqp=CAU"))
        sliderItems.add(SliderItems("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ0nzWfFNWdZKYeWV-I8Lm1u9BDUfGI0ls3Aw&s"))

        sliderAdapter = SliderAdapter(binding.viewpagerSlider, sliderItems)
        binding.viewpagerSlider.adapter = sliderAdapter
        binding.viewpagerSlider.offscreenPageLimit = 3
        binding.viewpagerSlider.clipToPadding = false
        binding.viewpagerSlider.getChildAt(0).overScrollMode
    }

    private fun initView() {
        RetrofitClient.instance.getCategories().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val categories = response.body()?.body ?: emptyList()
                    setupCategories(categories)
                } else {
                    Log.e("MainActivity", "Error fetching categories: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("MainActivity", "API call failed: ${t.message}")
            }
        })
    }

    private fun setupCategories(categories: List<Category>) {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewBestMovie.layoutManager = layoutManager
        binding.recyclerViewBestMovie.adapter = CategoryAdapter(categories)
    }


}
