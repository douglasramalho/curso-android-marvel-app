package com.example.marvelapp.presentation.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentDetailBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.extensions.showShortToast
import com.example.marvelapp.presentation.common.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val args by navArgs<DetailFragmentArgs>()

    @Suppress("MagicNumber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailViewArg = args.detailViewArg
        binding.imageCharacter.run {
            transitionName = detailViewArg.name
            imageLoader.load(this, detailViewArg.imageUrl)
        }

        setSharedElementTransitionOnEnter()

        loadCategoriesAndObserveUiState(detailViewArg)
        setAndObserveFavoriteUiState(detailViewArg)
    }

    private fun loadCategoriesAndObserveUiState(detailViewArg: DetailViewArg) {
        viewModel.categories.load(detailViewArg.characterId)
        viewModel.categories.state.observe(viewLifecycleOwner) { uiState ->
            binding.apply {
                flipperDetail.displayedChild = when (uiState) {
                    is UiActionStateLiveData.UiState.Loading -> FLIPPER_CHILD_POSITION_LOADING
                    is UiActionStateLiveData.UiState.Success -> {
                        recyclerParentDetail.run {
                            setHasFixedSize(true)
                            adapter = DetailParentAdapter(uiState.detailParentList, imageLoader)
                        }
                        FLIPPER_CHILD_POSITION_DETAIL
                    }
                    is UiActionStateLiveData.UiState.Error -> {
                        binding.includeErrorView.buttonTrying.setOnClickListener {
                            viewModel.categories.load(detailViewArg.characterId)
                        }
                        FLIPPER_CHILD_POSITION_ERROR
                    }
                    is UiActionStateLiveData.UiState.Empty -> FLIPPER_CHILD_POSITION_EMPTY
                }
            }
        }
    }

    private fun setAndObserveFavoriteUiState(detailViewArg: DetailViewArg) {
        viewModel.favorite.runCatching {
            checkFavorite(detailViewArg.characterId)

            binding.imageFavoriteIcon.setOnClickListener {
                update(detailViewArg)
            }

            state.observe(viewLifecycleOwner) { uiState ->
                binding.flipperFavorite.displayedChild = when (uiState) {
                    FavoriteUiActionStateLiveData.UiState.Loading -> FLIPPER_FAVORITE_CHILD_POSITION_LOADING
                    is FavoriteUiActionStateLiveData.UiState.Icon -> {
                        binding.imageFavoriteIcon.setImageResource(uiState.icon)
                        FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                    }
                    is FavoriteUiActionStateLiveData.UiState.Error -> {
                        showShortToast(uiState.messageResId)
                        FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                    }
                }
            }
        }

    }

    //Define a animação da transição como "move" id do receptor e do emisor deve ser o mesmo
    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
            sharedElementEnterTransition = this
        }
    }

    companion object {
        private const val FLIPPER_CHILD_POSITION_LOADING = 0
        private const val FLIPPER_CHILD_POSITION_DETAIL = 1
        private const val FLIPPER_CHILD_POSITION_ERROR = 2
        private const val FLIPPER_CHILD_POSITION_EMPTY = 3
        private const val FLIPPER_FAVORITE_CHILD_POSITION_IMAGE = 0
        private const val FLIPPER_FAVORITE_CHILD_POSITION_LOADING = 1
    }
}