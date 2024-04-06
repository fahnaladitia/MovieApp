package com.pahnal.submissioncapstone.core.utils.mappers

import com.pahnal.submissioncapstone.core.data.source.local.entity.MovieEntity
import com.pahnal.submissioncapstone.core.data.source.remote.response.MovieResponse
import com.pahnal.submissioncapstone.core.domain.model.Movie


fun MovieResponse.toDomain(): Movie {
    return Movie(
        id = id?.toInt() ?: 0,
        title = title ?: "",
        imageUrl = posterPath ?: "",
    )
}

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        imageUrl = imageUrl,
    )
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
    )
}