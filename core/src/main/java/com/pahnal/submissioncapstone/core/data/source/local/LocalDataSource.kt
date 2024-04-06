package com.pahnal.submissioncapstone.core.data.source.local

import com.pahnal.submissioncapstone.core.data.source.local.entity.MovieEntity
import com.pahnal.submissioncapstone.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {

    fun getAllMoviesFavorite(): Flow<List<MovieEntity>> {
        return movieDao.getAllMoviesFavorite()
    }

    suspend fun setFavorite(movieEntity: MovieEntity, state: Boolean) {
        if (state) {
            movieDao.saveMovie(movieEntity)
        } else {
            movieDao.removeMovie(movieEntity.id)
        }
    }

   suspend  fun checkMovieIn(movieId: Int): Boolean {
        val result = movieDao.checkInMovie(movieId)
        return result > 0
    }
}