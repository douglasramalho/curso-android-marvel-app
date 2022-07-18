package com.example.marvelapp.presentation.characters.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class CharacterLoadMoreStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CharacterLoadMoreStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = CharacterLoadMoreStateViewHolder.create(parent, retry)

    override fun onBindViewHolder(
        holder: CharacterLoadMoreStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}