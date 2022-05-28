package com.example.marvelapp.presentation.common.adapter

import androidx.recyclerview.widget.DiffUtil

class GenericDiffCallback<T: ListItem>: DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}