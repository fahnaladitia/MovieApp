package com.pahnal.submissioncapstone.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pahnal.submissioncapstone.core.domain.model.Movie
import com.pahnal.submissioncapstone.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch


class FavoriteViewModel(private val movieUseCase: MovieUseCase): ViewModel() {

     val movies = movieUseCase.getAllMoviesFavorite().asLiveData()

     fun setFavorite(movie: Movie, isFavorite: Boolean) {
          viewModelScope.launch {
               movieUseCase.setMovieFavorite(movie,isFavorite)
          }
     }

}