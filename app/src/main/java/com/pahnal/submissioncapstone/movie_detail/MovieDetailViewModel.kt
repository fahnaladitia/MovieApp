package com.pahnal.submissioncapstone.movie_detail

import androidx.lifecycle.ViewModel
import com.pahnal.submissioncapstone.core.domain.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor() : ViewModel() {


    val movieDetail: MovieDetail = MovieDetail(
        id = 823464,
        title = "Godzilla x Kong: The New Empire",
        imageUrl = "/tMefBSflR6PGQLv7WvFPpKLZkyk.jpg",
        backdropPath = "/sR0SpCrXamlIkYMdfz83sFn5JS6.jpg",
        genres = arrayListOf(
            "Action",
            "Science Fiction",
            "Adventure"
        ),
        overview = "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within our world, challenging their very existence – and our own.",
        rating = 6.699,
        popularity = 3673.963,
        revenue = 194000000.0,
        isFavorite = false,
    )
}