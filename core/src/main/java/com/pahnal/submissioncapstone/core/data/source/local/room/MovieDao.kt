package com.pahnal.submissioncapstone.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pahnal.submissioncapstone.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAllMoviesFavorite(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovie(movie: MovieEntity)
    @Query("DELETE FROM movies WHERE id =:movieId")
    suspend fun removeMovie(movieId: Int)

    @Query("SELECT count(*) FROM movies WHERE id =:movieId")
    suspend fun checkInMovie(movieId: Int): Int


}