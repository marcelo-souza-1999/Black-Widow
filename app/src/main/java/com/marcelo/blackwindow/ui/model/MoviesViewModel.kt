package com.marcelo.blackwindow.ui.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marcelo.blackwindow.R
import com.marcelo.blackwindow.data.model.Genery
import com.marcelo.blackwindow.data.model.Movie
import com.marcelo.blackwindow.data.model.SimilarMovies
import com.marcelo.blackwindow.data.remote.MoviesResult
import com.marcelo.blackwindow.data.repository.MoviesRepository


class MoviesViewModel(private val dataSource: MoviesRepository) : ViewModel() {

    val similarMoviesLiveData: MutableLiveData<List<SimilarMovies>> = MutableLiveData()
    val viewFlipperSimilarMoviesLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
    val movieLiveData: MutableLiveData<Movie> = MutableLiveData()
    val viewFlipperMovieLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
    val generyLiveData: MutableLiveData<List<Genery>> = MutableLiveData()

    companion object {
        private const val VIEW_FLIPPER_MOVIE = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }

    fun getMovieDetails() {
        dataSource.getMovieDetails { result: MoviesResult ->
            when(result) {
                is MoviesResult.SuccessMovieDetails -> {
                    movieLiveData.value = result.movie
                    viewFlipperMovieLiveData.value = Pair(VIEW_FLIPPER_MOVIE, null)
                }
                is MoviesResult.ApiErrorMovie ->
                {
                    if (result.statusCode == 401)
                    {
                        viewFlipperMovieLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.get_error_401)
                    }
                    else
                    {
                        viewFlipperMovieLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.get_error_404)
                    }
                }
                is MoviesResult.ServerError ->
                {
                    viewFlipperMovieLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_connection)
                }
            }
        }
    }

    fun getSimilarMovies()
    {
        dataSource.getSimilarMovies { result: MoviesResult ->
            when(result)
            {
                is MoviesResult.SuccessSimilarMovies ->
                {
                    similarMoviesLiveData.value = result.similarMovies
                    viewFlipperSimilarMoviesLiveData.value = Pair(VIEW_FLIPPER_MOVIE, null)
                }
                is MoviesResult.ApiErrorMovie -> {
                    if (result.statusCode == 401)
                    {
                        viewFlipperSimilarMoviesLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.get_error_401)
                    }
                    else
                    {
                        viewFlipperSimilarMoviesLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.get_error_404)
                    }
                }
                is MoviesResult.ServerError ->
                {
                    viewFlipperSimilarMoviesLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_connection)
                }
                is MoviesResult.SuccessGenre -> TODO()
                is MoviesResult.SuccessMovieDetails -> TODO()
            }
        }
    }

    fun getGenre() {
        dataSource.getGenre { result: MoviesResult ->
            when(result) {
                is MoviesResult.SuccessGenre -> {
                    generyLiveData.value = result.generies
                    viewFlipperSimilarMoviesLiveData.value = Pair(VIEW_FLIPPER_MOVIE, null)
                }
                is MoviesResult.ApiErrorMovie -> {
                    if (result.statusCode == 401) {
                        viewFlipperSimilarMoviesLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.get_error_401)
                    } else {
                        viewFlipperSimilarMoviesLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.get_error_404)
                    }
                }
                is MoviesResult.ServerError -> {
                    viewFlipperSimilarMoviesLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_connection)
                }
                is MoviesResult.SuccessMovieDetails -> TODO()
                is MoviesResult.SuccessSimilarMovies -> TODO()
            }
        }
    }

    class ViewModelFactory(private val dataSource: MoviesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
                return MoviesViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknow ViewModel class")
        }

    }
}