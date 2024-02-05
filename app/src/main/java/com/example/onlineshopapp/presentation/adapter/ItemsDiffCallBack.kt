package com.example.onlineshopapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.domain.entities.ItemEntity

class ItemsDiffCallBack: DiffUtil.ItemCallback<com.example.domain.domain.entities.ItemEntity>() {
    override fun areItemsTheSame(oldItem: com.example.domain.domain.entities.ItemEntity, newItem: com.example.domain.domain.entities.ItemEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: com.example.domain.domain.entities.ItemEntity, newItem: com.example.domain.domain.entities.ItemEntity): Boolean {
        return oldItem == newItem
    }
}