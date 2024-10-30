package com.example.buildmovieapponline.Model.DataTvShow

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TvShowRetrofitClient {
    private const val NEW_BASE_URL = "https://phimapi.com/"

    val instance: TvShowApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(NEW_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TvShowApiService::class.java)
    }
}
