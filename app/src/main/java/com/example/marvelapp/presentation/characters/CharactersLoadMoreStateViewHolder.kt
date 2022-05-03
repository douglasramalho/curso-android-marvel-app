package com.example.marvelapp.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.databinding.ItemCharacterLoadMoreStateBinding

class CharactersLoadMoreStateViewHolder(
    itemBinding: ItemCharacterLoadMoreStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {
    // itemview eh um carinha que eu ganho de free so de ter herdado do viewHolder
    private val binding = ItemCharacterLoadMoreStateBinding.bind(itemView)
    private val progressBarLoadingMore = binding.progressLoadingMore
    private val txtTryAgainMsg = binding.txtTryAgain.also {
        // vou recuperar esse textView aqui e fazer algo dentro desse textView aqui
        // antes de retornar pra minha variavel
        it.setOnClickListener {
            // vai ser uma funcao que vai ser passada aqui no nosso ViewHolder
            // ele vai passar esse carinha como se fosse um callBack pra quem chamou, pra que passou
            // essa funcao aqui
            retry()
        }

    }

    fun bind(loadState: LoadState) {
        // se der verdadeiro ele mostra o nosso progressBar
        // se der falso ele esconde o progressBar
        progressBarLoadingMore.isVisible = loadState is LoadState.Loading
        txtTryAgainMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CharactersLoadMoreStateViewHolder {
            val itemBinding =
                ItemCharacterLoadMoreStateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return CharactersLoadMoreStateViewHolder(itemBinding, retry)

        }
    }
}