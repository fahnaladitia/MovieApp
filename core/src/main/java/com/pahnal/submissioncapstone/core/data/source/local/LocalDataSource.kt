package com.pahnal.submissioncapstone.core.data.source.local

//@Singleton
//class LocalDataSource @Inject constructor(private val userDao: UserDao) {
//
//
//    fun getAllUser(query: String): Flow<List<UserEntity>> {
//        if (query.isEmpty()) {
//            return userDao.getAllUser()
//        }
//        return userDao.searchUsers(query)
//
//    }
//
//
//    fun getAllFavoriteUser(): Flow<List<UserEntity>> = userDao.getAllFavoriteUser()
//
//
//    suspend fun insertUsers(users: List<UserEntity>) = userDao.insertUsers(users)
//
//
//    fun updateFavoriteUser(user: UserEntity, newState: Boolean) {
//        user.isFavorite = newState
//        userDao.updateFavoriteUser(user)
//    }
//}