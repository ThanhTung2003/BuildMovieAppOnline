package com.example.buildmovieapponline.ModelApi

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val status: String,
                         val body: String,
                         val code: Int)

interface MovieApiService {

    @GET("xprogamer/api/internship/exercise_5/home_page")
    fun getCategories(): Call<ApiResponse>

    @GET("moviesByCategory")
    fun getMoviesByCategory(@Query("categoryId") categoryId: Int): Call<ApiResponse>

    @GET("search_movies")
    fun searchMovies(@Query("query") query: String): Call<ApiResponse>

    @POST("xprogamer/api/internship/exercise_5/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

}



