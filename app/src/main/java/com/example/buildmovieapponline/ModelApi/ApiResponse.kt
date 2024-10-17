package com.example.buildmovieapponline.ModelApi

data class ApiResponse(
    val status: String,
    val body: List<Category>
) {
    val movies: List<Movie>?
        get() {
            return movies
        }
}
