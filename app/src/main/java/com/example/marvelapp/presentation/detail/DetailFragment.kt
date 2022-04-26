package com.example.marvelapp.presentation.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.marvelapp.databinding.FragmentDetailBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    @Suppress("MagicNumber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailViewArg = args.detailViewArg
        binding.imageCharacter.run {
            transitionName = detailViewArg.name
            imageLoader.load(this, detailViewArg.imageUrl)
        }
        setSharedElementTransitionOnEnter()

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.apply {
                flipperDetail.displayedChild = when (uiState) {
                    is DetailViewModel.UiState.Loading -> FLIPPER_CHILD_POSITION_LOADING
                    is DetailViewModel.UiState.Success -> {
                        recyclerParentDetail.run {
                            setHasFixedSize(true)
                            adapter = DetailParentAdapter(uiState.detailParentList, imageLoader)
                        }
                        FLIPPER_CHILD_POSITION_DETAIL
                    }
                    is DetailViewModel.UiState.Error -> {
                        binding.includeErrorView.buttonTrying.setOnClickListener {
                            viewModel.getCharacterCategories(detailViewArg.characterId)
                        }
                        FLIPPER_CHILD_POSITION_ERROR
                    }
                    is DetailViewModel.UiState.Empty -> FLIPPER_CHILD_POSITION_EMPTY
                }
            }
        }
        viewModel.getCharacterCategories(detailViewArg.characterId)
    }

    //Define a animação da transição como "move" id do receptor e do emisor deve ser o mesmo
    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
            sharedElementEnterTransition = this
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_POSITION_LOADING = 0
        private const val FLIPPER_CHILD_POSITION_DETAIL = 1
        private const val FLIPPER_CHILD_POSITION_ERROR = 2
        private const val FLIPPER_CHILD_POSITION_EMPTY = 3
    }
}