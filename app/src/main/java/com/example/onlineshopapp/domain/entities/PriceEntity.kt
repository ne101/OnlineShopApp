package com.example.onlineshopapp.domain.entities

data class PriceEntity(
    val discount: Int,
    val price: String,
    val priceWithDiscount: String,
    val unit: String
)
