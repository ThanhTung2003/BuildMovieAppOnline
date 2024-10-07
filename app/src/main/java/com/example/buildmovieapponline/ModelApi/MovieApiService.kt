package com.example.buildmovieapponline.ModelApi

import retrofit2.Call
import retrofit2.http.GET

interface MovieApiService {
    @GET("xprogamer/api/internship/exercise_5/home_page")
    fun getCategories(): Call<ApiResponse>
}

