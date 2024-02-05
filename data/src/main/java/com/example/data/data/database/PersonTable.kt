package com.example.data.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("person_table")
data class PersonTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val secondName: String,
    val phone: String
)
