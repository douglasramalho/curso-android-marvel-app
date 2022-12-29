package com.example.marvelapp.presentation.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.marvelapp.databinding.FragmentFavoritesBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.getGenericAdapterOf
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val favoriteAdapter by lazy {
        getGenericAdapterOf {
            FavoriteViewHolder.create(it, imageLoader)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentFavoritesBinding.inflate(
        inflater, container, false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoritesAdapter()
        viewModel.state.observe(viewLifecycleOwner){ uiState ->
            binding.flipperFavorites.displayedChild = when(uiState) {
                is FavoritesViewModel.UiState.ShowFavorite -> {
                    favoriteAdapter.submitList(uiState.favorites)
                    FLIPPER_CHILD_CHARACTER
                }
                FavoritesViewModel.UiState.ShowEmpty -> {
                    favoriteAdapter.submitList(emptyList())
                    FLIPPER_CHILD_EMPTY
                }
            }
        }

        viewModel.getAll()
    }

    private fun initFavoritesAdapter(){
        binding.recyclerFavorites.run {
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private const val FLIPPER_CHILD_CHARACTER = 0
        private const val FLIPPER_CHILD_EMPTY = 1
    }
}