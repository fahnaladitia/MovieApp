package com.pahnal.submissioncapstone.core.data.source.remote.network

import com.pahnal.submissioncapstone.core.data.source.remote.response.MovieListResponse
import com.pahnal.submissioncapstone.core.utils.Constants.MOVIEDB_TOKEN_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/{type}")
    @Headers("Authorization: Bearer $MOVIEDB_TOKEN_KEY")
    suspend fun getMovieList(
        @Path("type") type: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): MovieListResponse


    @GET("search/movie")
    @Headers("Authorization: Bearer $MOVIEDB_TOKEN_KEY")
    suspend fun searchMovies(
        @Query("query") query: String = "a",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): MovieListResponse

}