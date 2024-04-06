package com.pahnal.submissioncapstone.favorite

import android.content.Context
import com.pahnal.submissioncapstone.di.MovieModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [MovieModuleDependencies::class])
interface MovieComponent {

    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapModuleDependencies: MovieModuleDependencies):Builder
        fun build(): MovieComponent
    }
}