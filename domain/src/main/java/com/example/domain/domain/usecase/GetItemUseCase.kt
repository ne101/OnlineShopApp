package com.example.domain.domain.usecase

import com.example.domain.domain.entities.ItemEntity
import com.example.domain.domain.repository.Repository
import javax.inject.Inject

class GetItemUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: String): ItemEntity {
        return repository.getItem(id)
    }
}