package com.example.buildmovieapponline.Model.DataTvShow

import com.example.buildmovieapponline.Model.DataTvShow.MovieDetail.TvShowDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface TvShowApiService {
    @GET("v1/api/danh-sach/tv-shows")
    fun getTvShow(): Call<TvShowResponse>

    @GET
    fun getTvShowDetails(@Url url: String): Call<TvShowDetailResponse>
}