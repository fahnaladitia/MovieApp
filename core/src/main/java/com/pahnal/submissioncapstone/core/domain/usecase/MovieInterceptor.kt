package com.pahnal.submissioncapstone.core.domain.usecase

import androidx.paging.PagingData
import com.pahnal.submissioncapstone.core.data.Resource
import com.pahnal.submissioncapstone.core.domain.enums.MovieType
import com.pahnal.submissioncapstone.core.domain.model.Movie
import com.pahnal.submissioncapstone.core.domain.model.MovieDetail
import com.pahnal.submissioncapstone.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInterceptor @Inject constructor(private val repository: IMovieRepository) :
    MovieUseCase {
    override fun getAllMovies(movieType: MovieType): Flow<PagingData<Movie>> {
        return repository.getAllMovies(movieType)
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return repository.searchMovies(query)
    }

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> {
        return repository.getMovieDetail(movieId)
    }

    override fun getAllMoviesFavorite(): Flow<List<Movie>> {
        return repository.getAllMovieFavorite()
    }

    override suspend fun setMovieFavorite(movie: Movie, isFavorite: Boolean) {
        return repository.setMovieFavorite(movie, isFavorite)
    }
}