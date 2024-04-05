package com.pahnal.submissioncapstone.core.di

//@Module
//@InstallIn(SingletonComponent::class)
//class DatabaseModule {
//
//    @Singleton
//    @Provides
//    fun provideDatabase(@ApplicationContext context: Context): UserDatabase = Room.databaseBuilder(
//        context,
//        UserDatabase::class.java, "Users.db",
//    ).fallbackToDestructiveMigration().build()
//
//    @Provides
//    fun provideUserDao(database: UserDatabase): UserDao = database.userDao()
//}