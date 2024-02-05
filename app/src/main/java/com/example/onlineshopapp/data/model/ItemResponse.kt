package com.example.onlineshopapp.data.model

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("items")
    val items: List<Item>
)