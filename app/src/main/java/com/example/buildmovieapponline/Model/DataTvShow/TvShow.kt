package com.example.buildmovieapponline.Model.DataTvShow

data class TvShow(
    val _id: String,
    val name: String,
    val slug: String,
    val origin_name: String,
    val poster_url: String,
    val thumb_url: String,
    val time: String,
    val episode_current: String,
    val quality: String,
    val lang: String,
    val year: Int,
    val category: List<CategoryTvShow>,
    val country: List<Country>
)
