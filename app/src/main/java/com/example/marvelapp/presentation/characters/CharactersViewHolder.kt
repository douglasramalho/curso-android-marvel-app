package com.example.marvelapp.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.core.domain.model.Character
import com.bumptech.glide.Glide
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ItemCharacterBinding

class CharactersViewHolder(
    itemCharacterBinding: ItemCharacterBinding
) : RecyclerView.ViewHolder(itemCharacterBinding.root) {
    private val textName = itemCharacterBinding.txtName
    private val imgCharacter = itemCharacterBinding.imgCharacter

    // o que tiver dentro do meu domain
    // todos os modelos as classes sera as classes que eu vou utilizar dentro da camada de apresentacao
    // sao classes puras sem anotacoes de framework nem nada
    // se no futuro precisamos converter pra um modelo de view
    // tudo o que vier da nossa camada de network das nossas request
    // das nossas classes de response
    // vamos transformar nesse dominio
    // da nossa aplicacao
    fun bind(character: Character) {
        textName.text = character.name
        Glide.with(itemView)
            .load(character.imgUrl)
            // se der erro ao baixar imagem ou timeout
            // por uma imagem padrao
            .fallback(R.drawable.ic_img_loading_error)
            .into(imgCharacter)
    }
    companion object{
        fun create(parent: ViewGroup): CharactersViewHolder{
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemCharacterBinding.inflate(inflater, parent, false)
            return CharactersViewHolder(itemBinding)

        }
    }
}