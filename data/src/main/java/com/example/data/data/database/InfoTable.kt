package com.example.data.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "info_table", foreignKeys = [ForeignKey(entity = ItemTable::class,
    parentColumns = ["id"],
    childColumns = ["itemId"],
    onDelete = ForeignKey.CASCADE)])
data class InfoTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemId: String,
    val title: String,
    val value: String
)