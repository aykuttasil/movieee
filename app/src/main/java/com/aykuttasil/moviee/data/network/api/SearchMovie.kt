package com.aykuttasil.moviee.data.network.api

import com.aykuttasil.moviee.data.network.MovieData
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchMovieResponse(
    val page: Int? = null,
    val results: List<MovieData>? = null
)