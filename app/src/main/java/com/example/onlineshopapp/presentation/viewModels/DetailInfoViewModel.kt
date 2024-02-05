package com.example.onlineshopapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailInfoViewModel @Inject constructor(
    private val getItemUseCase: com.example.domain.domain.usecase.GetItemUseCase,
    private val addFavouriteItemUseCase: com.example.domain.domain.usecase.AddFavouriteItemUseCase,
    private val removeFavouriteItemUseCase: com.example.domain.domain.usecase.RemoveFavouriteItemUseCase,
    private val getFavouriteItemsUseCase: com.example.domain.domain.usecase.GetFavouriteItemsUseCase
) : ViewModel() {
    private val _favouriteItems = MutableLiveData<com.example.domain.domain.entities.ItemsEntity>()
    val favouriteItems: LiveData<com.example.domain.domain.entities.ItemsEntity>
        get() = _favouriteItems

    private val _item = MutableLiveData<com.example.domain.domain.entities.ItemEntity>()
    val item: LiveData<com.example.domain.domain.entities.ItemEntity>
        get() = _item

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke())
        }
    }

    fun addItemInBD(itemEntity: com.example.domain.domain.entities.ItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            addFavouriteItemUseCase.invoke(itemEntity)
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke())
        }
    }

    fun removeItemFromBD(itemEntity: com.example.domain.domain.entities.ItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavouriteItemUseCase.invoke(itemEntity)
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke())
        }
    }

    fun getItem(id: String) {
        viewModelScope.launch {
            _item.postValue(getItemUseCase.invoke(id))
        }
    }
}