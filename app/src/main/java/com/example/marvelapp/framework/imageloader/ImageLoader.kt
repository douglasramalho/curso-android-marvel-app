package com.example.marvelapp.framework.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.marvelapp.R

interface ImageLoader {

    fun load(
        imageView: ImageView,
        imageUrl: String,
        @DrawableRes placeholder: Int = R.drawable.ic_img_placeholder,
        @DrawableRes fallback: Int = R.drawable.ic_img_loading_error
    )
}