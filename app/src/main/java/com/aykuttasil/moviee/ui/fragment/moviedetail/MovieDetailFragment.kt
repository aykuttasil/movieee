package com.aykuttasil.moviee.ui.fragment.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.aykuttasil.moviee.R
import com.aykuttasil.moviee.databinding.FragmentMovieDetailBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()



        viewModel.movieDetail(args.movieId)
    }

    private fun initViews() {
        binding.txtVote.text = args.vote

        binding.imgPoster.load(args.posterImageUrl)

        binding.toolbar.run {
            title = args.title
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun observeData() {
        viewModel.liveMovie.observe(viewLifecycleOwner) {
            binding.txtOverview.text = it.overview
            binding.imgBackdrop.load("https://image.tmdb.org/t/p/original/${it.backdropPath}")

            it.genres?.forEach { genre ->
                val chip = Chip(requireContext())
                chip.text = genre.name
                chip.setChipBackgroundColorResource(R.color.purple_200)

                binding.chipGroup.addView(chip)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}