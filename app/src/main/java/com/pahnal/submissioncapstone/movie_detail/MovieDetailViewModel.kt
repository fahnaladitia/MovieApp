package com.pahnal.submissioncapstone.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahnal.submissioncapstone.core.data.Resource
import com.pahnal.submissioncapstone.core.domain.model.MovieDetail
import com.pahnal.submissioncapstone.core.domain.usecase.MovieUseCase
import com.pahnal.submissioncapstone.core.utils.mappers.toMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData(null)
    val errorMessage: LiveData<String?> = _errorMessage


    private val _movieDetail: MutableLiveData<MovieDetail> = MutableLiveData()
    val movieDetail: LiveData<MovieDetail> = _movieDetail

    fun getMovieDetail(movieId: Int) {
        movieUseCase.getMovieDetail(movieId).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        _errorMessage.value = null
                        _movieDetail.value = result.data!!
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                        _errorMessage.value = null
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.message
                    }
                }

            }.launchIn(viewModelScope)
    }

    fun setFavoriteMovie(movie: MovieDetail, isFavorite: Boolean) {
        viewModelScope.launch {
            movieUseCase.setMovieFavorite(movie.toMovie(), isFavorite)
        }
    }


}