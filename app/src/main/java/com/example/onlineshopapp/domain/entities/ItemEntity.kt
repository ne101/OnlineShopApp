package com.example.onlineshopapp.domain.entities

import com.example.onlineshopapp.data.model.Feedback
import com.example.onlineshopapp.data.model.Info
import com.example.onlineshopapp.data.model.Price

data class ItemEntity(
    val available: Int,
    val description: String,
    val feedback: FeedBackEntity,
    val id: String,
    val info: List<InfoEntity>,
    val ingredients: String,
    val price: PriceEntity,
    val subtitle: String,
    val tags: List<String>,
    val title: String
)
