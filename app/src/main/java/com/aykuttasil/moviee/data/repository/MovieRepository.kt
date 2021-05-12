package com.aykuttasil.moviee.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aykuttasil.moviee.data.network.MovieService
import com.aykuttasil.moviee.data.network.api.MoviePagingSource
import com.aykuttasil.moviee.data.network.model.mapToEntity
import com.aykuttasil.moviee.di.NetworkModule
import com.aykuttasil.moviee.domain.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService
) {

    suspend fun movieDetail(movieId: Int): MovieEntity {
        val resp = movieService.movieDetail(movieId, NetworkModule.API_KEY)
        return resp.mapToEntity()
    }

    fun getSearchResultStream(query: String): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieService, query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}