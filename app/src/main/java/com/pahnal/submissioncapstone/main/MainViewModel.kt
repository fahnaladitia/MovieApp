package com.pahnal.submissioncapstone.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pahnal.submissioncapstone.core.domain.enums.MovieType
import com.pahnal.submissioncapstone.core.domain.model.Movie
import com.pahnal.submissioncapstone.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
) : ViewModel() {


    private val _movieType: MutableLiveData<MovieType> = MutableLiveData()
    val movieType: LiveData<MovieType> = _movieType

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _moviePagingList: MutableLiveData<PagingData<Movie>> = MutableLiveData()
    val moviePagingList: LiveData<PagingData<Movie>> = _moviePagingList


    init {
        getMovies(MovieType.POPULAR)
    }

    fun getMovies(stateMovieType: MovieType) {
        viewModelScope.launch {
            setLoading(true)
            val data = movieUseCase
                .getAllMovies(stateMovieType)
                .cachedIn(viewModelScope)
            data.collectLatest { movies ->
                setLoading(false)
                _movieType.value = stateMovieType
                _moviePagingList.value = movies
            }
        }
    }

    fun setFavorite(movie: Movie, isFavorite: Boolean) {
        viewModelScope.launch {
            movieUseCase.setMovieFavorite(movie, isFavorite)
        }
    }


    private fun setLoading(value: Boolean) {
        _isLoading.value = value
    }

}