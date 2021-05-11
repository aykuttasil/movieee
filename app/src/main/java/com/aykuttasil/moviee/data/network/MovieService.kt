package com.aykuttasil.moviee.data.network

import com.aykuttasil.moviee.data.network.api.SearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): SearchMovieResponse

}

