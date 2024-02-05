package com.example.onlineshopapp.domain.usecase

import com.example.onlineshopapp.domain.entities.ItemEntity
import com.example.onlineshopapp.domain.repository.Repository
import javax.inject.Inject

class GetItemUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: String): ItemEntity {
        return repository.getItem(id)
    }
}