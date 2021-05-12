package com.aykuttasil.moviee.ui.fragment.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aykuttasil.moviee.data.repository.MovieRepository
import com.aykuttasil.moviee.domain.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: Application,
    private val movieRepository: MovieRepository
) : AndroidViewModel(app) {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<MovieEntity>>? = null

    fun searchMovie(queryString: String): Flow<PagingData<MovieEntity>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<MovieEntity>> =
            movieRepository.getSearchResultStream(queryString)
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}