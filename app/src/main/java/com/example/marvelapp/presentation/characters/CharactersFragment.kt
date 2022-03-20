package com.example.marvelapp.presentation.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.core.domain.model.Character
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.example.marvelapp.presentation.about.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding: FragmentCharactersBinding get() = _binding!!

    private val viewModel: CharactersViewModel by viewModels()

    private val charactersAdapter = CharactersAdapter()

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

        lifecycleScope.launch {
            viewModel.charactersPagingData("").collect { pagingData ->
                charactersAdapter.submitData(pagingData)
            }
        }
    }

    private fun initCharactersAdapter() {
        with(binding.recyclerCharacters) {
            setHasFixedSize(true)
            adapter = charactersAdapter
        }
    }
}