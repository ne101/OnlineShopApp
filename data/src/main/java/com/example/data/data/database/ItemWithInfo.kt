package com.example.data.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class ItemWithInfo(
    @Embedded val item: ItemTable,
    @Relation(
        parentColumn = "id",
        entityColumn = "itemId"
    )
    val info: List<InfoTable>
)