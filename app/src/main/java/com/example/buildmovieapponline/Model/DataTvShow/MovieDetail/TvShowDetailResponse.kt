package com.example.buildmovieapponline.Model.DataTvShow.MovieDetail

import com.example.buildmovieapponline.Model.DataTvShow.MovieDetail.MovieDetails

data class TvShowDetailResponse(
    val status: Boolean,
    val msg: String,
    val movie: MovieDetails,
    val episodes: List<Episode>
)
