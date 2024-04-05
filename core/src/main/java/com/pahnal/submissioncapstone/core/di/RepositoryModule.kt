package com.pahnal.submissioncapstone.core.di

import com.pahnal.submissioncapstone.core.data.MovieRepository
import com.pahnal.submissioncapstone.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [
    NetworkModule::class,
//    DatabaseModule::class,
])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: MovieRepository): IMovieRepository


}