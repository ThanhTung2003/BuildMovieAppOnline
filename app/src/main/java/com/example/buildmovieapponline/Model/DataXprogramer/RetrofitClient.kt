package com.example.buildmovieapponline.Model.DataXprogramer

import com.example.buildmovieapponline.Const.CompanionObject.Companion.START_POINT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = START_POINT

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    private fun getOkhttp(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
        return client.build()
    }
    val instance: MovieApiService by lazy {
        val retrofit = Retrofit.Builder()
            .client(getOkhttp())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MovieApiService::class.java)
    }
}