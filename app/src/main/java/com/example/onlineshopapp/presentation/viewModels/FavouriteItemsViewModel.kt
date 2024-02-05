package com.example.onlineshopapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.domain.entities.ItemEntity
import com.example.domain.domain.usecase.AddFavouriteItemUseCase
import com.example.domain.domain.usecase.GetFavouriteItemUseCase
import com.example.domain.domain.usecase.GetFavouriteItemsUseCase
import com.example.domain.domain.usecase.RemoveFavouriteItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteItemsViewModel @Inject constructor(
    private val getFavouriteItemsUseCase: com.example.domain.domain.usecase.GetFavouriteItemsUseCase,
    private val addFavouriteItemUseCase: com.example.domain.domain.usecase.AddFavouriteItemUseCase,
    private val removeFavouriteItemUseCase: com.example.domain.domain.usecase.RemoveFavouriteItemUseCase,
    private val getFavouriteItemUseCase: com.example.domain.domain.usecase.GetFavouriteItemUseCase
) : ViewModel() {
    private val _favouriteItems = MutableLiveData<List<com.example.domain.domain.entities.ItemEntity>>()
    val favouriteItems: LiveData<List<com.example.domain.domain.entities.ItemEntity>>
        get() = _favouriteItems

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke().items)
        }
    }



    fun addItemInDB(item: com.example.domain.domain.entities.ItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            addFavouriteItemUseCase.invoke(item)
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke().items)
        }
    }

    fun removeItemFromDB(item: com.example.domain.domain.entities.ItemEntity) {
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