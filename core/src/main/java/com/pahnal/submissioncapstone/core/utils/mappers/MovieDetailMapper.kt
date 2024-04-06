package com.pahnal.submissioncapstone.core.utils.mappers

import com.pahnal.submissioncapstone.core.data.source.remote.response.MovieDetailResponse
import com.pahnal.submissioncapstone.core.domain.model.Movie
import com.pahnal.submissioncapstone.core.domain.model.MovieDetail


fun MovieDetailResponse.toDomain(): MovieDetail {
    return MovieDetail(
        id = id ?: 0,
        title = title ?: "",
        imageUrl = posterPath ?: "",
        backdropPath = backdropPath ?: "",
        genres = genres?.map {
            it?.name ?: ""
        } ?: emptyList(),
        overview = overview ?: "",
        revenue = revenue?.toDouble() ?: 0.0,
        rating = voteAverage?.toDouble() ?: 0.0,
        popularity = popularity?.toDouble() ?: 0.0,
        isFavorite = false,
    )
}

fun MovieDetail.toMovie(): Movie {
    return Movie(
        id, title, imageUrl
    )
}
