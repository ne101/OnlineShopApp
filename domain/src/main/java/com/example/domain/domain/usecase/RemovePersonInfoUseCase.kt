package com.example.domain.domain.usecase

import com.example.domain.domain.repository.Repository
import javax.inject.Inject

class RemovePersonInfoUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: Int) {
        repository.removePersonInfo(id)
    }
}