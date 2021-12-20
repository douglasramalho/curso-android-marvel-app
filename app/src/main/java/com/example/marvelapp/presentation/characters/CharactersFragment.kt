package com.example.marvelapp.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.core.domain.model.Character
import com.example.marvelapp.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding: FragmentCharactersBinding get() = _binding!!

    private val characterAdapter = CharacterAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCharactersBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharactersAdapter()

        characterAdapter.submitList(
            listOf(
                Character(
                    "Vitor",
                    "https://media.istockphoto.com/photos/friends-playing-with-shagai-together-picture-id1346324801"
                )
            )
        )
    }

    private fun initCharactersAdapter() {
        with(binding.recyclerCharacter) {
            setHasFixedSize(true) // quando os itens são do mesmo tamanho
            adapter = characterAdapter
        }
    }
}