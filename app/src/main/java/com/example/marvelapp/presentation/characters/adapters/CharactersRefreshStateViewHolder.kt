package com.example.marvelapp.presentation.characters.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.databinding.ItemCharacterLoadMoreStateBinding
import com.example.marvelapp.databinding.ItemCharacterRefreshStateBinding

class CharactersRefreshStateViewHolder(
    itemBinding: ItemCharacterRefreshStateBinding,
    retry: () -> Unit
): RecyclerView.ViewHolder(itemBinding.root) {
    private val binding = ItemCharacterRefreshStateBinding.bind(itemView)
    private val progressBarLoadingMore = binding.progressLoadingMore
    private val textTryAgainMessage = binding.textTryAgain.also {
        it.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState){
        progressBarLoadingMore.isVisible = loadState is LoadState.Loading
        textTryAgainMessage.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CharactersRefreshStateViewHolder {
            val itemBinding = ItemCharacterRefreshStateBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return CharactersRefreshStateViewHolder(itemBinding, retry)
        }
    }
}