package com.example.marvelapp.presentation.common

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class GenericViewHolder<T>(
    itemBinding: ViewBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    abstract fun bind(data: T)
}