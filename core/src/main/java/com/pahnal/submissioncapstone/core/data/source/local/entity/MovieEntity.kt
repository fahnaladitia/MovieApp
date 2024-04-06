package com.pahnal.submissioncapstone.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movies")
data class MovieEntity(

    @PrimaryKey
    val id: Int,
    val title: String,
    val imageUrl: String,
)