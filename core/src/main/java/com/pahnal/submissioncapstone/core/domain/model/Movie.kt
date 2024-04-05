package com.pahnal.submissioncapstone.core.domain.model

import android.os.Parcelable
import com.pahnal.submissioncapstone.core.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val imageUrl: String,
    var isFavorite: Boolean = false,
) : Parcelable {

    fun getPosterUrl(): String = Constants.IMAGE_URL + imageUrl
}

