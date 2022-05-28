package com.example.marvelapp.presentation.about

import androidx.fragment.app.Fragment
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentAboutBinding
import com.example.marvelapp.presentation.common.extensions.viewBinding

class AboutFragment : Fragment(R.layout.fragment_about) {

    private val binding by viewBinding(FragmentAboutBinding::bind)

    init {
        binding.text
    }
}