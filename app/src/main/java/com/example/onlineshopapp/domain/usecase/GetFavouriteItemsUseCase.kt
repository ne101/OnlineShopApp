package com.example.onlineshopapp.domain.usecase

import com.example.onlineshopapp.domain.entities.ItemsEntity
import com.example.onlineshopapp.domain.repository.Repository
import javax.inject.Inject

class GetFavouriteItemsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): ItemsEntity {
        return repository.getFavouriteItems()
    }
}