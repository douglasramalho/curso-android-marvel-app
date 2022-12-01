package com.example.marvelapp.framework.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.marvelapp.R
import javax.inject.Inject

class GlideImageLoaderImpl @Inject constructor() : ImageLoader {

    override fun loadImage(imageView: ImageView, imageUrl: String, fallback: Int) {
        Glide.with(imageView.rootView)
            .load(imageUrl)
            .fallback(R.drawable.ic_img_loading_error)
            .into(imageView)
    }
}