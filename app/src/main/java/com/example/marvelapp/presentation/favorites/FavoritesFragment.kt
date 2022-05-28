package com.example.marvelapp.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentFavoritesBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.adapter.getGenericAdapterOf
import com.example.marvelapp.presentation.common.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val viewModel: FavoritesViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val favoritesAdapter by lazy {
        getGenericAdapterOf {
            FavoritesViewHolder.create(it, imageLoader)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoritesAdapter()

        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperFavorite.displayedChild = when (uiState) {
                is FavoritesViewModel.UiState.ShowFavorite -> {
                    favoritesAdapter.submitList(uiState.favorites)
                    FLIPPER_CHILD_CHARACTERS
                }
                FavoritesViewModel.UiState.ShowEmpty -> {
                    favoritesAdapter.submitList(emptyList())
                    FLIPPER_CHILD_EMPTY
                }
            }
        }

        viewModel.getAll()
    }

    private fun initFavoritesAdapter() {
        binding.recyclerFavorites.run {
            setHasFixedSize(true)
            adapter = favoritesAdapter
        }
    }

    companion object {
        private const val FLIPPER_CHILD_CHARACTERS = 0
        private const val FLIPPER_CHILD_EMPTY = 1
    }
}