package com.example.marvelapp.presentation.common

interface ListItem {

    val key: Long

    fun areItemsTheSame(other: ListItem) = this.key == other.key

    fun areContensTheSame(other: ListItem) = this == other
}