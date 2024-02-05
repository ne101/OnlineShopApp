package com.example.onlineshopapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapp.domain.entities.ItemEntity
import com.example.onlineshopapp.domain.usecase.AddFavouriteItemUseCase
import com.example.onlineshopapp.domain.usecase.GetFavouriteItemUseCase
import com.example.onlineshopapp.domain.usecase.GetFavouriteItemsUseCase
import com.example.onlineshopapp.domain.usecase.RemoveFavouriteItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteItemsViewModel @Inject constructor(
    private val getFavouriteItemsUseCase: GetFavouriteItemsUseCase,
    private val addFavouriteItemUseCase: AddFavouriteItemUseCase,
    private val removeFavouriteItemUseCase: RemoveFavouriteItemUseCase,
    private val getFavouriteItemUseCase: GetFavouriteItemUseCase
) : ViewModel() {
    private val _favouriteItems = MutableLiveData<List<ItemEntity>>()
    val favouriteItems: LiveData<List<ItemEntity>>
        get() = _favouriteItems

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke().items)
        }
    }



    fun addItemInDB(item: ItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            addFavouriteItemUseCase.invoke(item)
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke().items)
        }
    }

    fun removeItemFromDB(item: ItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavouriteItemUseCase.invoke(item)
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke().items)
        }
    }

    fun getFavouriteItem(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFavouriteItemUseCase.invoke(id)
        }
    }
}