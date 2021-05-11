package com.aykuttasil.moviee.ui.fragment.moviedetail

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
class MovieDetailViewModel @Inject constructor(
    app: Application,
    private val movieRepository: MovieRepository
) : AndroidViewModel(app) {

    val liveMovie: MutableLiveData<MovieEntity> = MutableLiveData()

    fun movieDetail(id: Int) {
        viewModelScope.launch {
            liveMovie.value = movieRepository.movieDetail(id)
        }
    }

}