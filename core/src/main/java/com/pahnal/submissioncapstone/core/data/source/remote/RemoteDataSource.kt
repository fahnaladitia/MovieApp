package com.pahnal.submissioncapstone.core.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pahnal.submissioncapstone.core.data.source.remote.network.ApiService
import com.pahnal.submissioncapstone.core.data.source.remote.response.MovieResponse
import com.pahnal.submissioncapstone.core.domain.enums.MovieType
import com.pahnal.submissioncapstone.core.utils.MoviePagingSource
import com.pahnal.submissioncapstone.core.utils.MoviePagingType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

//    suspend fun searchMovies(query: String): Flow<ApiResponse<SearchUsersResponse>> =
//        flow {
//            try {
//                val response = apiService.getSearchUser(query)
//
//                if (response.items.isNullOrEmpty()) {
//                    emit(ApiResponse.Empty)
//                } else {
//                    emit(ApiResponse.Success(response))
//                }
//
//            } catch (e: HttpException) {
//                emit(ApiResponse.Error(e.localizedMessage?.toString() ?: ""))
//                Log.e(TAG, "searchUsers: HttpException", e)
//            } catch (e: Exception) {
//                emit(ApiResponse.Error(e.localizedMessage?.toString() ?: ""))
//                Log.e(TAG, "searchUsers: Exception", e)
//            }
//        }.flowOn(Dispatchers.IO)

    fun getMovieList(movieType: MovieType): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MoviePagingSource(apiService, MoviePagingType.SearchMovieByType(movieType))
            }
        ).flow.flowOn(Dispatchers.IO)
    }

    fun searchMovies(query: String): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MoviePagingSource(apiService, MoviePagingType.SearchMovieByQuery(query))
            }
        ).flow.flowOn(Dispatchers.IO)
    }

    companion object {
        private val TAG = RemoteDataSource::class.java.simpleName
    }

}