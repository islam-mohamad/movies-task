package com.islam.tasks.movies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.islam.tasks.movies.databinding.ItemMovieBinding
import com.islam.tasks.movies.presentation.uimodel.MovieUiModel
import com.squareup.picasso.Picasso

class MoviesAdapter(private val itemClick: (MovieUiModel) -> Unit) :
    ListAdapter<MovieUiModel, MoviesAdapter.MealViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { itemClick(item) }
    }

    class MealViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieUiModel) = with(binding) {
            tvTitle.text = item.title
            tvReleaseDate.text = item.releaseDate
            Picasso.get().load(item.posterPath).fit().into(ivPoster)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MovieUiModel>() {
        override fun areItemsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: MovieUiModel, newItem: MovieUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}