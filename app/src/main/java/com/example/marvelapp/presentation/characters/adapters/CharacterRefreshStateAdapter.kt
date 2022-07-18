package com.example.marvelapp.presentation.characters.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class CharacterRefreshStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CharacterRefreshStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = CharacterRefreshStateViewHolder.create(parent, retry)

    override fun onBindViewHolder(
        holder: CharacterRefreshStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}