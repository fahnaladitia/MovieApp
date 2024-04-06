package com.pahnal.submissioncapstone.core.ui

import com.pahnal.submissioncapstone.core.domain.model.Movie

interface OnClickListenerMovieAdapter {
    fun onClick(movie: Movie)
    fun onClickButtonFavorite(position: Int)
}