package com.example.domain.domain.usecase

import androidx.lifecycle.LiveData
import com.example.domain.domain.entities.ItemsEntity
import com.example.domain.domain.repository.Repository
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(private val repository: Repository){
    operator fun invoke(): LiveData<ItemsEntity> {
        return repository.getItems()
    }
}