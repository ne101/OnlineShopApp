package com.example.domain.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.domain.entities.ItemEntity
import com.example.domain.domain.entities.ItemsEntity
import com.example.domain.domain.entities.PersonEntity

interface Repository {
    fun getItems(): LiveData<ItemsEntity>
    suspend fun getItem(id: String): ItemEntity
    suspend fun getFavouriteItems(): ItemsEntity
    suspend fun getFavouriteItem(id: String): ItemEntity
    suspend fun addFavouriteItem(itemEntity: ItemEntity)
    suspend fun removeFavouriteItem(itemEntity: ItemEntity)
    suspend fun getPersonInfo(): PersonEntity?
    suspend fun addPersonInfo(personEntity: PersonEntity)
    suspend fun removePersonInfo(id: Int)
}