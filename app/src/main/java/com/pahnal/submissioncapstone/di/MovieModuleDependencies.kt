package com.pahnal.submissioncapstone.di

import com.pahnal.submissioncapstone.core.domain.usecase.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MovieModuleDependencies {

    fun movieUseCase(): MovieUseCase
}