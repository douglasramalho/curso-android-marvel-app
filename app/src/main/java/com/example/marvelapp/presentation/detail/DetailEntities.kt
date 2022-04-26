package com.example.marvelapp.presentation.detail

import androidx.annotation.StringRes

data class DetailChildVE(
    val id: Int,
    val imageUrl: String
)

data class DetailParentsVE(
    @StringRes
    val categoryStringResId: Int,
    val detailChildList: List<DetailChildVE> = listOf()
)