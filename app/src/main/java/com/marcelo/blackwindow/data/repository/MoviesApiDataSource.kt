package com.marcelo.blackwindow.data.repository

import com.marcelo.blackwindow.data.response.genre.GenresBodyResponse
import com.marcelo.blackwindow.data.model.Genery
import com.marcelo.blackwindow.data.model.SimilarMovies
import com.marcelo.blackwindow.data.remote.MoviesResult
import com.marcelo.blackwindow.data.remote.TheMovieApi
import com.marcelo.blackwindow.data.response.movies.MovieBodyResponse
import com.marcelo.blackwindow.data.response.similar.SimilarMoviesBodyResponse
import com.marcelo.blackwindow.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesApiDataSource : MoviesRepository
{
    private val GET_KEY = Constants.API_KEY
    private val MOVIE_ID = Constants.MOVIE_ID

    override fun getMovieDetails(movieResultCallback: (result: MoviesResult) -> Unit)
    {
        TheMovieApi.retrofit.getMovieDetails(movie_id = MOVIE_ID, api_key = GET_KEY).enqueue(object:
            Callback<MovieBodyResponse>
        {
            override fun onResponse(call: Call<MovieBodyResponse>, response: Response<MovieBodyResponse>)
            {
                when {
                    response.isSuccessful ->
                    {
                        movieResultCallback(MoviesResult.SuccessMovieDetails(response.body()?.getMovie()))

                    }
                    else -> {
                        movieResultCallback(MoviesResult.ApiErrorMovie(response.code()))
                    }
                }
            }

            override fun onFailure(call: Call<MovieBodyResponse>, t: Throwable)
            {
               movieResultCallback(MoviesResult.ServerError)
            }
        })
    }

    override fun getSimilarMovies(similarMovieResultCallback: (result: MoviesResult) -> Unit) {
        TheMovieApi.retrofit.getSimilarMovies(movie_id = MOVIE_ID, api_key = GET_KEY).enqueue(object : Callback<SimilarMoviesBodyResponse>
        {
            override fun onResponse(
                call: Call<SimilarMoviesBodyResponse>,
                response: Response<SimilarMoviesBodyResponse>
            ) {
                when
                {
                    response.isSuccessful ->
                    {
                        val similarMovies: MutableList<SimilarMovies> = mutableListOf()

                        response.body()?.let { similarMoviesBodyResponse ->
                            for (result in similarMoviesBodyResponse.results)
                            {
                                val similar = result.getSimilarMovies()
                                similarMovies.add(similar)
                            }
                        }
                        similarMovieResultCallback(MoviesResult.SuccessSimilarMovies(similarMovies))
                    }
                    else ->
                    {
                        similarMovieResultCallback(MoviesResult.ApiErrorMovie(response.code()))
                    }
                }
            }

            override fun onFailure(call: Call<SimilarMoviesBodyResponse>, t: Throwable)
            {
                similarMovieResultCallback(MoviesResult.ServerError)
            }

        })
    }

    override fun getGenre(genreMovieResultCallback: (result: MoviesResult) -> Unit)
    {
        TheMovieApi.retrofit.getGenre(api_key = GET_KEY).enqueue(object : Callback<GenresBodyResponse>
        {
            override fun onResponse(
                call: Call<GenresBodyResponse>,
                response: Response<GenresBodyResponse>
            )
            {
                when
                {
                    response.isSuccessful ->
                    {
                        val generies: MutableList<Genery> = mutableListOf()

                        response.body()?.let { genresBodyResponse ->
                            for (result in genresBodyResponse.genres)
                            {
                                val genre = result.getGenre()
                                generies.add(genre)
                            }
                        }
                        genreMovieResultCallback(MoviesResult.SuccessGenre(generies))
                    }
                    else ->
                    {
                        genreMovieResultCallback(MoviesResult.ApiErrorMovie(response.code()))
                    }
                }
            }

            override fun onFailure(call: Call<GenresBodyResponse>, t: Throwable)
            {
               genreMovieResultCallback(MoviesResult.ServerError)
            }

        })
    }
}