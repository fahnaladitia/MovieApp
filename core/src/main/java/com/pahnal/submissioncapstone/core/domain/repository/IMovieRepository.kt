package com.pahnal.submissioncapstone.core.domain.repository

import androidx.paging.PagingData
import com.pahnal.submissioncapstone.core.domain.enums.MovieType
import com.pahnal.submissioncapstone.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getAllMovies(movieType: MovieType): Flow<PagingData<Movie>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
}
