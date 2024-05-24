package com.example.marvelapp.presentation.characters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.databinding.IncludeViewCharactersLoadingStateBinding
import com.example.marvelapp.databinding.ItemCharacterLoadModeStateBinding

class CharactersLoadMoreStateViewHolder(
    itemBinding: ItemCharacterLoadModeStateBinding,
    retry: () -> Unit
): RecyclerView.ViewHolder(itemBinding.root) {

    private val binding = ItemCharacterLoadModeStateBinding.bind(itemView)
    private val progressBarLoadingMore = binding.progressLoadingMore
    private val textTryAgainMessage = binding.textTryAgain.also {
        it.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        progressBarLoadingMore.isVisible = loadState is LoadState.Loading
        textTryAgainMessage.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CharactersLoadMoreStateViewHolder {
            val itemBinding = ItemCharacterLoadModeStateBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

            return CharactersLoadMoreStateViewHolder(itemBinding, retry)
        }
    }
}