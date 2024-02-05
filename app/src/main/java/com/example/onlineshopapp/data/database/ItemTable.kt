package com.example.onlineshopapp.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.onlineshopapp.domain.entities.FeedBackEntity
import com.example.onlineshopapp.domain.entities.PriceEntity

@Entity("favourite_item_table")
data class ItemTable(
    @PrimaryKey val id: String,
    val available: Int,
    val description: String,
    @Embedded(prefix = "feedback_") val feedback: FeedBackEntity,
    val ingredients: String,
    @Embedded(prefix = "price_") val price: PriceEntity,
    val subtitle: String,
    val tags: String,
    val title: String
)
