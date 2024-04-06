package com.pahnal.submissioncapstone.core.domain.usecase

import androidx.paging.PagingData
import com.pahnal.submissioncapstone.core.data.Resource
import com.pahnal.submissioncapstone.core.domain.enums.MovieType
import com.pahnal.submissioncapstone.core.domain.model.Movie
import com.pahnal.submissioncapstone.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovies(movieType: MovieType): Flow<PagingData<Movie>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>

    fun getAllMoviesFavorite(): Flow<List<Movie>>

    suspend fun setMovieFavorite(movie: Movie, isFavorite: Boolean)
}