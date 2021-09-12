package com.marcelo.blackwindow.data.remote

import com.marcelo.blackwindow.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TheMovieApi {

    private const val BASE_URL = Constants.BASE_URL

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val retrofit: TheMovieServices = initRetrofit().create(TheMovieServices::class.java)
}