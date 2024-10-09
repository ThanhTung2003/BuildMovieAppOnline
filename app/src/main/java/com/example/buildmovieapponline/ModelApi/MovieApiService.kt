package com.example.buildmovieapponline.ModelApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("xprogamer/api/internship/exercise_5/home_page")
    fun getCategories(): Call<ApiResponse>

    @GET("search_movies")
    fun searchMovies(@Query("query") query: String): Call<ApiResponse>
}

