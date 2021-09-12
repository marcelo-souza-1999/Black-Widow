package com.marcelo.blackwindow.data.remote

import com.marcelo.blackwindow.data.model.Genery
import com.marcelo.blackwindow.data.model.Movie
import com.marcelo.blackwindow.data.model.SimilarMovies

sealed class MoviesResult {
    class SuccessSimilarMovies(val similarMovies: List<SimilarMovies>) : MoviesResult()
    class SuccessMovieDetails(val movie: Movie?) : MoviesResult()
    class SuccessGenre(val generies: List<Genery>) : MoviesResult()
    class ApiErrorMovie(val statusCode: Int) : MoviesResult()
    object ServerError : MoviesResult()
}
