package com.aykuttasil.moviee.domain

data class MovieEntity(
    val id: Int? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val overview: String? = null,
    val voteAverage: Double? = 0.0,
    val genres: List<GenreEntity>? = null
)

data class GenreEntity(
    val id: Int? = null,
    val name: String? = null
)