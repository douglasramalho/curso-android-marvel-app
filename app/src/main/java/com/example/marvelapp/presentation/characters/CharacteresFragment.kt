package com.example.marvelapp.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.marvelapp.databinding.FragmentCharacteresBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacteresFragment : Fragment() {

    private var _binding: FragmentCharacteresBinding? = null
    private val binding: FragmentCharacteresBinding get() = _binding!!

    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var characterAdapter: CharactersAdapter

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
        observeInitialLoadState()

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.charactersPageData(query = "").collect { pagingData ->
                    characterAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun initCharactersAdapter() {
        characterAdapter = CharactersAdapter()
        with(binding.recyclerCharacter) {
            scrollToPosition(0)
            setHasFixedSize(true)
            adapter = characterAdapter.withLoadStateFooter(
                footer = CharactersLoadStateAdapter(
                    retry = characterAdapter::retry
                )
            )
        }
    }

    private fun observeInitialLoadState() {
        lifecycleScope.launch {
            characterAdapter.loadStateFlow.collectLatest { loadState ->
                binding.flipperCharacters.displayedChild = when (loadState.refresh) {
                    is LoadState.Loading -> {
                        setShimmerVisibility(true)
                        FLIPPER_CHILD_LOAD
                    }
                    is LoadState.NotLoading -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_CHARACTERS
                    }
                    is LoadState.Error -> {
                        setShimmerVisibility(false)
                        binding.includeViewCharactersErrorState.buttonRetry.setOnClickListener {
                            characterAdapter.refresh()
                        }
                        FLIPPER_CHILD_ERROR
                    }
                }
            }
        }
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewCharactersLoadingState.shimmerCharacters.run {
            isVisible = visibility
            if (visibility) startShimmer() else stopShimmer()
        }
    }

    companion object {
        private const val FLIPPER_CHILD_LOAD = 0
        private const val FLIPPER_CHILD_CHARACTERS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }
}
