package com.example.buildmovieapponline.Model

import com.example.buildmovieapponline.ModelApi.MovieApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://testapi.io/api/"

    val instance: MovieApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MovieApiService::class.java)
    }
}