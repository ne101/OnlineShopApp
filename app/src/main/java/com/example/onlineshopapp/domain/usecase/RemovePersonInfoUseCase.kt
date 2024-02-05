package com.example.onlineshopapp.domain.usecase

import com.example.onlineshopapp.domain.repository.Repository
import javax.inject.Inject

class RemovePersonInfoUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: Int) {
        repository.removePersonInfo(id)
    }
}