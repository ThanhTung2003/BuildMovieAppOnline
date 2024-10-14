package com.example.buildmovieapponline.ModelApi

data class Movie(
    val id: String,
    val logo: String,
    val name: String,
    val category: String,
    val duration: Int,
    val description: String
)
