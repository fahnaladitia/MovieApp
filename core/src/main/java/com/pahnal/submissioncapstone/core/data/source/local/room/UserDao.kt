package com.pahnal.submissioncapstone.core.data.source.local.room

//@Dao
//interface UserDao {
//
//    @Query("SELECT * FROM users WHERE username LIKE '%' || :query || '%' ORDER BY username ASC")
//    fun searchUsers(query: String): Flow<List<UserEntity>>
//
//    @Query("SELECT * FROM users ORDER BY username ASC")
//    fun getAllUser(): Flow<List<UserEntity>>
//
//    @Query("SELECT * FROM users WHERE isFavorite = 1")
//    fun getAllFavoriteUser(): Flow<List<UserEntity>>
//
//    @Query("SELECT * FROM users WHERE users.username = :username")
//    fun getUserById(username: String): Flow<UserEntity>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertUsers(users: List<UserEntity>)
//
//    @Update
//    fun updateFavoriteUser(user: UserEntity)
//
//}