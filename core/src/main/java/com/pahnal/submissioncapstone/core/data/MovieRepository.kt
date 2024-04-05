package com.pahnal.submissioncapstone.core.data

import androidx.paging.PagingData
import androidx.paging.map
import com.pahnal.submissioncapstone.core.data.source.remote.RemoteDataSource
import com.pahnal.submissioncapstone.core.data.source.remote.response.MovieResponse
import com.pahnal.submissioncapstone.core.domain.enums.MovieType
import com.pahnal.submissioncapstone.core.domain.model.Movie
import com.pahnal.submissioncapstone.core.domain.repository.IMovieRepository
import com.pahnal.submissioncapstone.core.utils.mappers.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
//    private val localDataSource: LocalDataSource,
//    private val appExecutors: AppExecutors,
): IMovieRepository {
    //    override fun getAllMovies(movieType: MovieType): Flow<Resource<List<Movie>>> = flow {
//        emit(Resource.Loading())
//        when(val response = remoteDataSource.getMovieList(movieType).first()) {
//            is ApiResponse.Success -> {
//                val data = response.data.toDomains()
//                emit(Resource.Success(data))
//            }
//            is ApiResponse.Error -> {
//                emit(Resource.Error(response.errorMessage))
//            }
//            is ApiResponse.Empty -> {
//                emit(Resource.Success(emptyList()))
//            }
//        }
//    }
    override fun getAllMovies(movieType: MovieType): Flow<PagingData<Movie>> {
        return remoteDataSource.getMovieList(movieType).map { pagingData ->
            pagingData.map(MovieResponse::toDomain)
        }
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return  remoteDataSource.searchMovies(query).map { pagingData ->
            pagingData.map(MovieResponse::toDomain)
        }
    }


}