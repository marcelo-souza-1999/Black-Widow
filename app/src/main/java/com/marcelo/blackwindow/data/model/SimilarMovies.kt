package com.marcelo.blackwindow.data.model

data class SimilarMovies(
    val id: Int,
    val title: String,
    val release_date: String,
    val poster_path: String?,
    val genre_ids: List<Int>
)