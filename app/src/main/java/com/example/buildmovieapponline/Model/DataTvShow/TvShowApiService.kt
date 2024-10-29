package com.example.buildmovieapponline.Model.DataTvShow

import retrofit2.Call
import retrofit2.http.GET

interface TvShowApiService {
    @GET("v1/api/danh-sach/tv-shows")
    fun getTvShow(): Call<TvShowResponse>
}