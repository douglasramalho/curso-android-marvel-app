package com.example.marvelapp.framework.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoader {

    fun loadImage(imageView: ImageView, imageUrl: String, @DrawableRes fallback: Int)
}