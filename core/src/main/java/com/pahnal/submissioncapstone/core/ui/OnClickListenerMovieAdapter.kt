package com.pahnal.submissioncapstone.core.ui

import com.pahnal.submissioncapstone.core.domain.model.Movie

abstract class OnClickListenerMovieAdapter {
    open fun onClick(movie: Movie) {}
    open fun onClickButtonFavorite(position: Int) {}
}