package com.marcelo.blackwindow.data.repository

import com.marcelo.blackwindow.data.remote.MoviesResult

interface MoviesRepository {
    fun getMovieDetails(movieResultCallback: (result: MoviesResult) -> Unit)
    fun getSimilarMovies(similarMovieResultCallback: (result: MoviesResult) -> Unit)
    fun getGenre(genreMovieResultCallback: (result: MoviesResult) -> Unit)
}