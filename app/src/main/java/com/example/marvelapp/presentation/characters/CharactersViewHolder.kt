package com.example.marvelapp.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Character
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.util.onCharacterItemClick

class CharactersViewHolder(
    itemCharacterBinding: ItemCharacterBinding,
    val imageLoader: ImageLoader,
    private val onItemClickListener: onCharacterItemClick
) : RecyclerView.ViewHolder(itemCharacterBinding.root) {

    private val textName = itemCharacterBinding.textName
    private val imageCharacter = itemCharacterBinding.imageCharacter

    fun bind(character: Character) {
        textName.text = character.name
        imageCharacter.transitionName = character.name
        imageLoader.loadImage(imageCharacter, character.imageUrl, R.drawable.ic_img_loading_error)
        itemView.setOnClickListener {
            onItemClickListener.invoke(character, imageCharacter)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            imageLoader: ImageLoader,
            onItemClickListener: (character: Character, view: View) -> Unit
        ): CharactersViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemCharacterBinding.inflate(inflater, parent, false)
            return CharactersViewHolder(itemBinding, imageLoader, onItemClickListener)
        }
    }
}