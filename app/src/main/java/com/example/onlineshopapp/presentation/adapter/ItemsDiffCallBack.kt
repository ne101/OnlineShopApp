package com.example.onlineshopapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.onlineshopapp.domain.entities.ItemEntity

class ItemsDiffCallBack: DiffUtil.ItemCallback<ItemEntity>() {
    override fun areItemsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean {
        return oldItem == newItem
    }
}