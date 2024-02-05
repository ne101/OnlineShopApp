package com.example.onlineshopapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.onlineshopapp.data.model.ItemResponse
import com.example.onlineshopapp.domain.entities.ItemsEntity
import com.example.onlineshopapp.domain.repository.Repository
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(private val repository: Repository){
    operator fun invoke(): LiveData<ItemsEntity> {
        return repository.getItems()
    }
}