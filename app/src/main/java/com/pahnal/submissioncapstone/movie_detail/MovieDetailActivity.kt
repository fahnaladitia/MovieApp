package com.pahnal.submissioncapstone.movie_detail

import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.pahnal.submissioncapstone.core.R
import com.pahnal.submissioncapstone.core.domain.model.Movie
import com.pahnal.submissioncapstone.core.domain.model.MovieDetail
import com.pahnal.submissioncapstone.core.utils.round
import com.pahnal.submissioncapstone.databinding.ActivityMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    private val viewModel: MovieDetailViewModel by viewModels()

    private lateinit var movieDetail: MovieDetail
    private  var movie: Movie? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()

    }

    private fun setupUI() {
        movie = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable(EXTRA_MOVIE,Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.extras?.getParcelable(EXTRA_MOVIE)
        }
        movieDetail = viewModel.movieDetail
        with(binding) {
            Glide.with(this@MovieDetailActivity)
                .load(movieDetail.getBackdropUrl())
                .centerCrop()
                .placeholder(R.drawable.item_placeholder)
                .into(ivBackdropMovie)

            Glide.with(this@MovieDetailActivity)
                .load(movieDetail.getPosterUrl())
                .override(130,175)
                .centerCrop()
                .placeholder(R.drawable.item_placeholder)
                .into(ivMovie)

            tvTitle.text = movieDetail.title
            tvRating.text = movieDetail.rating.round(2).toString()
            ratingBar.progress = (movieDetail.rating / 2).toInt()
            tvPopularityValue.text = movieDetail.popularity.toString()
            tvRevenueValue.text =NumberFormat.getCurrencyInstance(Locale.US).format(movieDetail.revenue)
            tvGenres.text = movieDetail.genres.joinToString(" • ")
            tvOverviewValue.text = movieDetail.overview

            if (movieDetail.isFavorite) {
                btnFavorite.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_favorite_red,
                        null
                    )
                )
            } else {
                btnFavorite.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_favorite_white,
                        null
                    )
                )
            }

        }
    }

    private fun setupButton() {

    }

    private fun setupObservers() {}


    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

}