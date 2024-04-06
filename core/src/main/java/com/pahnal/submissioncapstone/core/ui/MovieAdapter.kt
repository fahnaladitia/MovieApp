package com.pahnal.submissioncapstone.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pahnal.submissioncapstone.core.R
import com.pahnal.submissioncapstone.core.databinding.ItemMovieBinding
import com.pahnal.submissioncapstone.core.domain.model.Movie

class MovieAdapter(
    private val onClick: (movie: Movie) -> Unit,
    private val onClickButtonFavorite: (movie: Movie, position: Int) -> Unit,
) : ListAdapter<Movie, MovieAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class MyViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, position: Int) {
            Glide.with(itemView.context)
                .load(movie.getPosterUrl())
                .centerCrop()
                .placeholder(R.drawable.item_placeholder)
                .into(binding.ivMovie)
            setStatusFavorite(movie.isFavorite, itemView.context)
            binding.btnFavorite.setOnClickListener {
                onClickButtonFavorite(movie, position)
            }

            binding.tvTitle.text = movie.title
            binding.root.setOnClickListener {
                onClick(movie)
            }
        }

        private fun setStatusFavorite(statusFavorite: Boolean, context: Context) {
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    if (statusFavorite) R.drawable.ic_favorite_red else R.drawable.ic_favorite_white
                )
            )
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}