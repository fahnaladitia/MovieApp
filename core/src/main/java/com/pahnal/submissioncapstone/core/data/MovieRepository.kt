package com.pahnal.submissioncapstone.core.data

import androidx.paging.PagingData
import androidx.paging.map
import com.pahnal.submissioncapstone.core.data.source.local.LocalDataSource
import com.pahnal.submissioncapstone.core.data.source.remote.RemoteDataSource
import com.pahnal.submissioncapstone.core.data.source.remote.network.ApiResponse
import com.pahnal.submissioncapstone.core.domain.enums.MovieType
import com.pahnal.submissioncapstone.core.domain.model.Movie
import com.pahnal.submissioncapstone.core.domain.model.MovieDetail
import com.pahnal.submissioncapstone.core.domain.repository.IMovieRepository
import com.pahnal.submissioncapstone.core.utils.mappers.toDomain
import com.pahnal.submissioncapstone.core.utils.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IMovieRepository {

    override fun getAllMovies(movieType: MovieType): Flow<PagingData<Movie>> {
        return remoteDataSource.getMovieList(movieType).map { pagingData ->
            pagingData.map { response ->
                val data = response.toDomain()
                val isFavorite = localDataSource.checkMovieIn(data.id)
                data.isFavorite = isFavorite
                data
            }
        }
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return remoteDataSource.searchMovies(query).map { pagingData ->
            pagingData.map { response ->
                val data = response.toDomain()
                val isFavorite = localDataSource.checkMovieIn(data.id)
                data.isFavorite = isFavorite
                data
            }
        }
    }

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> = flow {

        emit(Resource.Loading())
        when (val response = remoteDataSource.getMovieDetail(movieId).first()) {
            is ApiResponse.Success -> {
                val data = response.data.toDomain()
                val isFavorite = localDataSource.checkMovieIn(data.id)
                data.isFavorite = isFavorite
                emit(Resource.Success(data))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }

    override fun getAllMovieFavorite(): Flow<List<Movie>> {
        return localDataSource.getAllMoviesFavorite()
            .map { entities ->
                entities.map { entity ->
                    val data = entity.toDomain()
                    data.isFavorite = true
                    data
                }
            }
    }

    override suspend fun setMovieFavorite(movie: Movie, isFavorite: Boolean) {
        val movieEntity = movie.toEntity()

        return localDataSource.setFavorite(movieEntity, isFavorite)
    }


}