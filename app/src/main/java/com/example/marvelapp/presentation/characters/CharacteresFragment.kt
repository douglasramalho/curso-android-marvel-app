package com.example.marvelapp.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.marvelapp.databinding.FragmentCharacteresBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacteresFragment : Fragment() {

    private var _binding: FragmentCharacteresBinding? = null
    private val binding: FragmentCharacteresBinding get() = _binding!!

    private val characterAdapter = CharactersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCharacteresBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharactersAdapter()
    }

    private fun initCharactersAdapter() {
        with(binding.recyclerCharacter) {
            setHasFixedSize(true)
            adapter = characterAdapter
        }
    }
}
