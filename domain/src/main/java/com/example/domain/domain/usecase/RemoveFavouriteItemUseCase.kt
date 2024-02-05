package com.example.domain.domain.usecase

import com.example.domain.domain.entities.ItemEntity
import com.example.domain.domain.repository.Repository
import javax.inject.Inject

class RemoveFavouriteItemUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(itemEntity: ItemEntity) {
        repository.removeFavouriteItem(itemEntity)
    }
}