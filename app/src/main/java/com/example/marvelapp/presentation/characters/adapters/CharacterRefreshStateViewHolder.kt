package com.example.marvelapp.presentation.characters.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.databinding.ItemCharacterLoadMoreStateBinding
import com.example.marvelapp.databinding.ItemCharacterRefreshStateBinding

class CharacterRefreshStateViewHolder(
    itemBinding: ItemCharacterRefreshStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val binding = ItemCharacterRefreshStateBinding.bind(itemView)
    private val progressBarLoadMore = binding.progressLoadingMore
    private val textTryingAgainMessage = binding.textTryAgain.also {
        it.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        progressBarLoadMore.isVisible = loadState is LoadState.Loading
        textTryingAgainMessage.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CharacterRefreshStateViewHolder {
            val itemBinding = ItemCharacterRefreshStateBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CharacterRefreshStateViewHolder(itemBinding, retry)
        }
    }
}