package com.pahnal.submissioncapstone.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.pahnal.submissioncapstone.core.domain.enums.MovieType
import com.pahnal.submissioncapstone.core.domain.model.Movie
import com.pahnal.submissioncapstone.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _movieType: MutableLiveData<MovieType> = MutableLiveData()
    val movieType: LiveData<MovieType> = _movieType

    private val _errorText: MutableLiveData<String?> = MutableLiveData()
    val errorText: LiveData<String?> = _errorText

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _movieList: MutableLiveData<List<Movie>> = MutableLiveData()
    val movieList: LiveData<List<Movie>> = _movieList

    private val _moviePagingList: MutableLiveData<PagingData<Movie>> = MutableLiveData()
    val moviePagingList: LiveData<PagingData<Movie>> = _moviePagingList


    init {
        getMovies(MovieType.POPULAR)
    }

    fun getMovies(stateMovieType: MovieType) {
        viewModelScope.launch {
            setLoading(true)
            val data = movieUseCase.getAllMovies(stateMovieType)
            data.collectLatest { movies ->
                setLoading(false)
                _movieType.value = stateMovieType
                _moviePagingList.value = movies
            }
//            data.collect { result ->
//                when (result) {
//                    is Resource.Loading -> {
//                        setLoading(true)
//                        setError()
//                    }
//
//                    is Resource.Error -> {
//                        setLoading(false)
//                        setError(result.message)
//                    }
//
//                    is Resource.Success -> {
//                        setLoading(false)
//                        setError()
//                        setMovieData(stateMovieType, result.data!!)
//                    }
//
//                }
//
//            }

        }
    }

    private fun setMovieData(movieType: MovieType, movies: List<Movie>) {
        _movieType.value = movieType
        _movieList.value = movies
    }

    private fun setLoading(value: Boolean) {
        _isLoading.value = value
    }

    private fun setError(value: String? = null) {
        _errorText.value = value
    }

}