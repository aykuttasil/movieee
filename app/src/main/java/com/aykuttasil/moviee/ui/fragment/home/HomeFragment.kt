package com.aykuttasil.moviee.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.aykuttasil.moviee.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var searchJob: Job? = null
    private var lastSearchQuery: String? = null

    private val movieListAdapter = MovieListAdapter {
        val direction = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
            it.id!!,
            it.title ?: "",
            it.voteAverage?.toString() ?: "0",
            "https://image.tmdb.org/t/p/w500/${it.posterPath}"
        )
        findNavController().navigate(direction)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY)

        binding.inputMovieDetail.doOnTextChanged { text, _, _, count ->
            if (count > 1 && lastSearchQuery != text.toString()) {
                search(text.toString())
                lastSearchQuery = text.toString()
            }
        }

        /*
        viewLifecycleOwner.lifecycleScope.launch {
            movieListAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.listMovie.scrollToPosition(0) }
        }
        */

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.inputMovieDetail.text?.trim().toString())
    }

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchMovie(query).collectLatest {
                movieListAdapter.submitData(it)
            }
        }
    }

    private fun initViews() {
        val recyclerView = binding.listMovie
        recyclerView.adapter = movieListAdapter

        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
    }
}