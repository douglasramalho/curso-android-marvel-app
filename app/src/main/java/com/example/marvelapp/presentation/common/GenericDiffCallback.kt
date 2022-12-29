package com.example.marvelapp.presentation.common

import androidx.recyclerview.widget.DiffUtil

class GenericDiffCallback<T: ListItem> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areContensTheSame(newItem)
    }
}