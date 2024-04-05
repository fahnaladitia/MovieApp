package com.pahnal.submissioncapstone.core.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pahnal.submissioncapstone.core.data.source.remote.network.ApiService
import com.pahnal.submissioncapstone.core.data.source.remote.response.MovieResponse
import com.pahnal.submissioncapstone.core.domain.enums.MovieType
import com.pahnal.submissioncapstone.core.utils.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

//    suspend fun searchUsers(query: String): Flow<ApiResponse<SearchUsersResponse>> =
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
//        try {
//            val type: String = when (movieType) {
//                MovieType.POPULAR -> "popular"
//                MovieType.NOW_PLAYING -> "now_playing"
//                MovieType.TOP_RATED -> "top_rated"
//                MovieType.UPCOMING -> "upcoming"
//            }
//
//            val response = apiService.getMovieList(type, page = page)
//
//            if (response.results.isNullOrEmpty()) {
//                emit(ApiResponse.Empty)
//                return@flow
//            }
//            emit(ApiResponse.Success(response))
//        } catch (e: HttpException) {
//            emit(ApiResponse.Error(e.localizedMessage?.toString() ?: ""))
//            Log.e(TAG, "HttpException", e)
//        } catch (e: IOException) {
//            emit(ApiResponse.Error(e.localizedMessage?.toString() ?: ""))
//            Log.e(TAG, "Exception", e)
//        }
//    }.flowOn(Dispatchers.IO)

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MoviePagingSource(apiService,movieType)
            }
        ).flow
    }

    companion object {
        private val TAG = RemoteDataSource::class.java.simpleName
    }

}