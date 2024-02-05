package com.example.onlineshopapp.domain.usecase

import com.example.onlineshopapp.domain.entities.PersonEntity
import com.example.onlineshopapp.domain.repository.Repository
import javax.inject.Inject

class AddPersonInfoUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(personEntity: PersonEntity) {
        repository.addPersonInfo(personEntity)
    }
}