package com.example.onlineshopapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapp.domain.entities.ItemEntity
import com.example.onlineshopapp.domain.entities.ItemsEntity
import com.example.onlineshopapp.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailInfoViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase,
    private val addFavouriteItemUseCase: AddFavouriteItemUseCase,
    private val removeFavouriteItemUseCase: RemoveFavouriteItemUseCase,
    private val getFavouriteItemsUseCase: GetFavouriteItemsUseCase
) : ViewModel() {
    private val _favouriteItems = MutableLiveData<ItemsEntity>()
    val favouriteItems: LiveData<ItemsEntity>
        get() = _favouriteItems

    private val _item = MutableLiveData<ItemEntity>()
    val item: LiveData<ItemEntity>
        get() = _item

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke())
        }
    }

    fun addItemInBD(itemEntity: ItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            addFavouriteItemUseCase.invoke(itemEntity)
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke())
        }
    }

    fun removeItemFromBD(itemEntity: ItemEntity) {
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