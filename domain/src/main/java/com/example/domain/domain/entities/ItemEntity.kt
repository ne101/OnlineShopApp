package com.example.domain.domain.entities

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
