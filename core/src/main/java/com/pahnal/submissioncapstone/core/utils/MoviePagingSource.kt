package com.pahnal.submissioncapstone.core.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pahnal.submissioncapstone.core.data.source.remote.network.ApiService
import com.pahnal.submissioncapstone.core.data.source.remote.response.MovieResponse
import com.pahnal.submissioncapstone.core.domain.enums.MovieType
import okio.IOException
import retrofit2.HttpException

sealed class MoviePagingType {
    data class SearchMovieByQuery(val query: String) : MoviePagingType()
    data class SearchMovieByType(val movieType: MovieType) : MoviePagingType()
}

class MoviePagingSource(
    private val apiService: ApiService,
    private val moviePagingType: MoviePagingType
) : PagingSource<Int, MovieResponse>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1

    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val pageIndex = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val movies: List<MovieResponse>? = when (moviePagingType) {
                is MoviePagingType.SearchMovieByType -> {
                    val type: String = when (moviePagingType.movieType) {
                        MovieType.POPULAR -> "popular"
                        MovieType.NOW_PLAYING -> "now_playing"
                        MovieType.TOP_RATED -> "top_rated"
                        MovieType.UPCOMING -> "upcoming"
                    }
                    apiService.getMovieList(type = type, page = pageIndex).results
                }

                is MoviePagingType.SearchMovieByQuery -> {
                    apiService.searchMovies(query = moviePagingType.query, page = pageIndex).results
                }
            }

            val nextKey = if (movies.isNullOrEmpty()) {
                null
            } else {
                pageIndex + (params.loadSize / 20)
            }
            LoadResult.Page(
                data = movies ?: emptyList(),
                prevKey = if (pageIndex == INITIAL_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}