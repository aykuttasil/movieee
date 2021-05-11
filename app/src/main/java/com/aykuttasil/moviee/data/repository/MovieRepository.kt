package com.aykuttasil.moviee.data.repository

import com.aykuttasil.moviee.data.network.MovieService
import com.aykuttasil.moviee.di.NetworkModule
import com.aykuttasil.moviee.domain.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService
) {

    suspend fun searchMovie(query: String, page: Int = 1): List<MovieEntity> {
        val resp = movieService.searchMovie(query, page, NetworkModule.API_KEY)
        return resp.results?.map {
            MovieEntity(it.id, it.title)
        } ?: emptyList()
    }
}