package com.example.domain.domain.usecase

import com.example.domain.domain.entities.ItemsEntity
import com.example.domain.domain.repository.Repository
import javax.inject.Inject

class GetFavouriteItemsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): ItemsEntity {
        return repository.getFavouriteItems()
    }
}