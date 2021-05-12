package com.aykuttasil.moviee.data.network.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aykuttasil.moviee.data.network.MovieService
import com.aykuttasil.moviee.data.network.model.MovieData
import com.aykuttasil.moviee.data.network.model.mapToEntity
import com.aykuttasil.moviee.di.NetworkModule
import com.aykuttasil.moviee.domain.MovieEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchMovieResponse(
    val page: Int? = null,
    val results: List<MovieData>? = null
)

class MoviePagingSource(
    private val movieService: MovieService,
    private val query: String
) : PagingSource<Int, MovieEntity>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieEntity> {
        return try {
            val position = params.key ?: 1
            val nextPageNumber = params.key ?: 1
            val response = movieService.searchMovie(query, nextPageNumber, NetworkModule.API_KEY)

            LoadResult.Page(
                data = response.results?.map { it.mapToEntity() } ?: emptyList(),
                prevKey = if (position == 1) null else position - 1,
                nextKey = response.page?.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
