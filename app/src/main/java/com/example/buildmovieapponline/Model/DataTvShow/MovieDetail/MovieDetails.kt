package com.example.buildmovieapponline.Model.DataTvShow.MovieDetail

import com.example.buildmovieapponline.Model.DataTvShow.Country

data class MovieDetails(
    val tmdb: Tmdb,
    val imdb: Imdb,
    val created: Created,
    val modified: Modified,
    val _id: String,
    val name: String,
    val slug: String,
    val origin_name: String,
    val content: String,
    val type: String,
    val status: String,
    val poster_url: String,
    val thumb_url: String,
    val is_copyright: Boolean,
    val sub_docquyen: Boolean,
    val chieurap: Boolean,
    val trailer_url: String,
    val time: String,
    val episode_current: String,
    val episode_total: String,
    val quality: String,
    val lang: String,
    val notify: String,
    val showtimes: String,
    val year: Int,
    val view: Int,
    val actor: List<String>,
    val director: List<String>,
    val category: List<CategoryMovieDetail>,
    val country: List<Country>
)
