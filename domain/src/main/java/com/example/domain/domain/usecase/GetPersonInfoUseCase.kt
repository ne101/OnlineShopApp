package com.example.domain.domain.usecase

import com.example.domain.domain.entities.PersonEntity
import com.example.domain.domain.repository.Repository
import javax.inject.Inject

class GetPersonInfoUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): PersonEntity? {
        return repository.getPersonInfo()
    }
}