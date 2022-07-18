package com.example.marvelapp.presentation.characters

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.characters.adapters.CharacterAdapter
import com.example.marvelapp.presentation.characters.adapters.CharacterLoadMoreStateAdapter
import com.example.marvelapp.presentation.characters.adapters.CharacterRefreshStateAdapter
import com.example.marvelapp.presentation.common.extensions.viewBinding
import com.example.marvelapp.presentation.detail.DetailViewArg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_characters) {

    private val binding by viewBinding(FragmentCharactersBinding::bind)

    private val viewModel: CharactersViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val headerAdapter: CharacterRefreshStateAdapter by lazy {
        CharacterRefreshStateAdapter(
            characterAdapter::retry
        )
    }

    private val characterAdapter: CharacterAdapter by lazy {
        CharacterAdapter(imageLoader) { character, view ->
            val extras = FragmentNavigatorExtras(
                view to character.name
            )
            val directions = CharactersFragmentDirections
                .actionCharactersFragmentToDetailFragment(
                    character.name,
                    DetailViewArg(
                        characterId = character.id,
                        name = character.name,
                        imageUrl = character.imageUrl
                    )
                )
            findNavController().navigate(directions, extras)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharactersAdapter()
        observerInitialLoadingState()

        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is CharactersViewModel.UiState.SearchResult -> {
                    characterAdapter.submitData(viewLifecycleOwner.lifecycle, uiState.data)
                }
            }
        }
        viewModel.searchCharacters()
    }

    private fun initCharactersAdapter() {
        postponeEnterTransition()
        with(binding.recyclerCharacter) {
            setHasFixedSize(true) // quando osgit itens são do mesmo tamanho
            adapter = characterAdapter.withLoadStateHeaderAndFooter(
                header = headerAdapter,
                footer = CharacterLoadMoreStateAdapter(characterAdapter::retry)
            )
            viewTreeObserver.addOnDrawListener {
                startPostponedEnterTransition()
            }
        }
    }

    private fun observerInitialLoadingState() {
        lifecycleScope.launchWhenCreated {
            characterAdapter.loadStateFlow.collectLatest { loadState ->
                headerAdapter.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf {
                        it is LoadState.Error && characterAdapter.itemCount > 0
                    } ?: loadState.prepend

                binding.flipperCharacters.displayedChild = when {
                    loadState.mediator?.refresh is LoadState.Loading -> {
                        setShimmerVisibility(true)
                        FLIPPER_CHILD_LOADING
                    }
                    loadState.mediator?.refresh is LoadState.Error
                            && characterAdapter.itemCount == 0 -> {
                        setShimmerVisibility(false)
                        binding.includeViewCharactersErrorState.buttonTrying.setOnClickListener {
                            characterAdapter.retry()
                        }
                        FLIPPER_CHILD_ERROR
                    }
                    loadState.source.refresh is LoadState.NotLoading
                            || loadState.mediator?.refresh is LoadState.NotLoading -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_CHARACTERS
                    }
                    else -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_CHARACTERS
                    }
                }
            }
        }
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewCharactersLoadingState.shimmerCharacters.run {
            isVisible = visibility
            if (visibility) {
                startShimmer()
            } else stopShimmer()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.characteres_menu_itens, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort -> {
                findNavController().navigate(R.id.action_charactersFragment_to_sortFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_CHARACTERS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }
}