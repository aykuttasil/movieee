package com.aykuttasil.moviee.ui.fragment.moviedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aykuttasil.moviee.R
import com.aykuttasil.moviee.ui.fragment.home.HomeViewModel

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}