package com.pahnal.submissioncapstone.movie_detail

import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.pahnal.submissioncapstone.core.R
import com.pahnal.submissioncapstone.core.domain.model.Movie
import com.pahnal.submissioncapstone.core.domain.model.MovieDetail
import com.pahnal.submissioncapstone.core.utils.round
import com.pahnal.submissioncapstone.core.utils.toGone
import com.pahnal.submissioncapstone.core.utils.toVisible
import com.pahnal.submissioncapstone.databinding.ActivityMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    private val viewModel: MovieDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->

            // get statusBar height
            val statusBar = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            (binding.fab.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBar
            windowInsets
        }

        setupExtras()
        setupObservers()
        binding.fab.setOnClickListener {
            finish()
        }
    }

    private fun setupExtras() {
        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable(EXTRA_MOVIE, Movie::class.java)
        } else {
            @Suppress("DEPRECATION") intent.extras?.getParcelable(EXTRA_MOVIE)
        }
        if (movie != null) {
            viewModel.getMovieDetail(movie.id)
        }
    }


    private fun updateUIMovieDetail(movieDetail: MovieDetail) {
        with(binding) {
            Glide.with(this@MovieDetailActivity).load(movieDetail.getBackdropUrl()).centerCrop()
                .placeholder(R.drawable.item_placeholder).into(ivBackdropMovie)

            Glide.with(this@MovieDetailActivity).load(movieDetail.getPosterUrl()).override(130, 175)
                .centerCrop().placeholder(R.drawable.item_placeholder).into(ivMovie)

            tvTitle.text = movieDetail.title
            tvRating.text = movieDetail.rating.round(2).toString()
            ratingBar.progress = (movieDetail.rating / 2).toInt()
            tvPopularityValue.text = movieDetail.popularity.toString()
            tvRevenueValue.text =
                NumberFormat.getCurrencyInstance(Locale.US).format(movieDetail.revenue)
            tvGenres.text = movieDetail.genres.joinToString(" • ")
            tvOverviewValue.text = movieDetail.overview
            setStatusFavorite(movieDetail.isFavorite)
        }
    }

    private fun setupButton(movieDetail: MovieDetail) {

        binding.btnFavorite.setOnClickListener {
            val isFavorite = !movieDetail.isFavorite
            movieDetail.isFavorite = isFavorite
            viewModel.setFavoriteMovie(movieDetail, isFavorite)
            setStatusFavorite(movieDetail.isFavorite)
        }
    }

    private fun setupObservers() {
        viewModel.movieDetail.observe(this) { value ->
            updateUIMovieDetail(value)
            setupButton(value)
        }
        viewModel.isLoading.observe(this) { value ->
            if (value) {
                binding.progressBar.toVisible()
            } else {
                binding.progressBar.toGone()

            }
        }
        viewModel.errorMessage.observe(this) { value ->
            if (value.isNullOrEmpty()) {
                binding.tvError.toGone()
            } else {
                binding.tvError.toVisible()
            }
            binding.tvError.text = value ?: ""
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        binding.btnFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (statusFavorite) R.drawable.ic_favorite_red else R.drawable.ic_favorite_white
            )
        )
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()

        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

}