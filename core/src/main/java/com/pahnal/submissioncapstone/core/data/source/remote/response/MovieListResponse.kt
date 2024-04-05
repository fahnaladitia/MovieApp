package com.pahnal.submissioncapstone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieListResponse(

	@field:SerializedName("page")
	val page: Number? = null,

	@field:SerializedName("results")
	val results: List<MovieResponse>? = null
)

data class MovieResponse(

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("original_title")
	val originalTitle: String? = null,

	@field:SerializedName("id")
	val id: Number? = null,

	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@field:SerializedName("genre_ids")
	val genreIds: List<Number?>? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("video")
	val video: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("popularity")
	val popularity: Number? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Number? = null,

	@field:SerializedName("vote_count")
	val voteCount: Number? = null
)
