package com.aykuttasil.moviee.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aykuttasil.moviee.databinding.ItemMovieBinding
import com.aykuttasil.moviee.domain.MovieEntity

class MovieListAdapter :
    ListAdapter<MovieEntity, MovieListAdapter.MovieViewHolder>(DataMovieItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            binding.txtTitle.text = movie.title
            binding.imgMovie.load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
            binding.txtScore.text = movie.voteAverage.toString()
        }
    }

    class DataMovieItemCallback : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(
            oldItem: MovieEntity,
            newItem: MovieEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MovieEntity,
            newItem: MovieEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}