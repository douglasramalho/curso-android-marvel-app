package com.example.marvelapp.presentation.characters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.dio.core.domain.model.Character

// vamos implementar essa ListAdapter pois dps vamos implementar outro adapter da biblioteca de pagging 3
// vai nos ajudar na paginacao e scrool infinito
// no lugar de ListAdapter eu vou chamar o PagingDataAdapter ele tem a mesma estrutura do listAdapter
// esse adaptador que eh do tipo PagingDataAdapter ele vai controlar pra gente as paginations
// quando ele detectar que o scrool chegou na ultima posicao
// ele vai automaticamente requisitar novos dados para o pagingSource
// e automaticamente  como estamos escutando esse fluxo de dados
// ele vai chamar, ele vai fazer com que os dados retorne pra camada de apresentacao de forma automatica
// sem que eu tenha que fazer nada.
// isso so e possivel pq estamos trabalhando com o flow


class CharactersAdapter : PagingDataAdapter<Character, CharactersViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        getItem(position)?.let {
            // se o item na posicao x nao for nulo me entrega um personagem
            holder.bind(it)
        }
    }

    companion object {
        // como funciona esse DIFF util?
        // o que esse objeto faz?
        // ele implementa esse DiffUtil ItemCallback do nosso Character
        // com isso o nosso list adapter vai conseguir saber se
        // quando eu tiver fazendo alguma operacao de remover
        // alterar ou adicionar ele vai saber
        // se o item que esta sendo recebido
        // se ele eh o mesmo que esta na lista
        // como ele sabe disso?
        // atraves dessas 2 funcoes
        // que checa se os itens sao o mesmo e conteudo e o mesmo
        // e ele faz isso com base no tipo que passamos  aqui
        private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem

            }

        }
    }
}