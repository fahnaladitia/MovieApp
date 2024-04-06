package com.pahnal.submissioncapstone.di

import com.pahnal.submissioncapstone.core.domain.usecase.MovieInterceptor
import com.pahnal.submissioncapstone.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideMovieUseCase(interceptor: MovieInterceptor): MovieUseCase

}
