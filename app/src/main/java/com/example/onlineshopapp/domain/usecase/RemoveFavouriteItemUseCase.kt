package com.example.onlineshopapp.domain.usecase

import com.example.onlineshopapp.domain.entities.ItemEntity
import com.example.onlineshopapp.domain.repository.Repository
import javax.inject.Inject

class RemoveFavouriteItemUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(itemEntity: ItemEntity) {
        repository.removeFavouriteItem(itemEntity)
    }
}