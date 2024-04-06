package com.pahnal.submissioncapstone.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pahnal.submissioncapstone.core.domain.model.Movie
import com.pahnal.submissioncapstone.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    state: SavedStateHandle,
) : ViewModel() {

    private val _currentQuery: MutableLiveData<String> = MutableLiveData()
    val currentQuery: LiveData<String> = _currentQuery

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _moviePagingList: MutableLiveData<PagingData<Movie>> = MutableLiveData()
    val moviePagingList: LiveData<PagingData<Movie>> = _moviePagingList

    private var job: Job? = null


    init {
        searchMovies("a")
    }

    fun searchMovies(query: String) {
        _currentQuery.value = query
        job?.cancel()
        job = viewModelScope.launch {
            delay(500L)
            setLoading(true)
            movieUseCase.searchMovies(query)
                .cachedIn(viewModelScope)
                .collectLatest { movies ->
                    setLoading(false)
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