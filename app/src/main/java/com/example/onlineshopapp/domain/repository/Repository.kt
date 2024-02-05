package com.example.onlineshopapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.onlineshopapp.domain.entities.ItemEntity
import com.example.onlineshopapp.domain.entities.ItemsEntity
import com.example.onlineshopapp.domain.entities.PersonEntity

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