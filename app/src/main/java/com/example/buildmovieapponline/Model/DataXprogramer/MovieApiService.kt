package com.example.buildmovieapponline.Model.DataXprogramer

import com.example.buildmovieapponline.Model.DataLogin.LoginRequest
import com.example.buildmovieapponline.Model.DataLogin.LoginResponse
import com.example.buildmovieapponline.Model.DataVideo.VideoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
//    xprogamer

    @GET("xprogamer/api/internship/exercise_5/home_page")
    fun getCategories(): Call<ApiResponse>

    @GET("moviesByCategory")
    fun getMoviesByCategory(@Query("categoryId") categoryId: Int): Call<ApiResponse>

    @GET("search_movies")
    fun searchMovies(@Query("query") query: String): Call<ApiResponse>

    @POST("xprogamer/api/internship/exercise_5/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("xprogamer/api/internship/exercise_5/play/{id}")
    fun getVideoLink(@Path("id") movieId: String): Call<VideoResponse>




}