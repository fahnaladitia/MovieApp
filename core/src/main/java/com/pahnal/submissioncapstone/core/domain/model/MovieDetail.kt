package com.pahnal.submissioncapstone.core.domain.model

import android.os.Parcelable
import com.pahnal.submissioncapstone.core.utils.Constants
import kotlinx.parcelize.Parcelize


@Parcelize
data class MovieDetail(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val backdropPath: String,
    val genres: List<String>,
    val overview: String,
    val rating: Double,
    val popularity: Double,
    val revenue: Double,
    var isFavorite: Boolean = false,
): Parcelable {
    fun getPosterUrl(): String = Constants.IMAGE_URL + imageUrl
    fun getBackdropUrl(): String = Constants.IMAGE_URL + backdropPath
}

