package com.aykuttasil.moviee.ui.fragment.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aykuttasil.moviee.data.repository.MovieRepository
import com.aykuttasil.moviee.domain.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: Application,
    private val movieRepository: MovieRepository
) : AndroidViewModel(app) {

    val liveMovie: MutableLiveData<List<MovieEntity>> = MutableLiveData()

    fun searchMovie(query: String, page: Int = 1) {
        viewModelScope.launch {
            liveMovie.value = movieRepository.searchMovie(query, page)
        }

        /*
        val list = mutableListOf<MovieEntity>()
        for (i in 1..10) {
            list.add(MovieEntity(i, "Movie $i - $query"))
        }
        liveMovie.value = list
        */
    }


}