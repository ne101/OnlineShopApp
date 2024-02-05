package com.example.onlineshopapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.domain.entities.ItemEntity
import com.example.domain.domain.entities.ItemsEntity
import com.example.domain.domain.entities.PriceEntity
import com.example.domain.domain.usecase.AddFavouriteItemUseCase
import com.example.domain.domain.usecase.GetFavouriteItemsUseCase
import com.example.domain.domain.usecase.GetItemsUseCase
import com.example.domain.domain.usecase.RemoveFavouriteItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val getItemsUseCase: com.example.domain.domain.usecase.GetItemsUseCase,
    private val addFavouriteItemUseCase: com.example.domain.domain.usecase.AddFavouriteItemUseCase,
    private val removeFavouriteItemUseCase: com.example.domain.domain.usecase.RemoveFavouriteItemUseCase,
    private val getFavouriteItemsUseCase: com.example.domain.domain.usecase.GetFavouriteItemsUseCase
) : ViewModel() {

    private val _favouriteItems = MutableLiveData<List<com.example.domain.domain.entities.ItemEntity>>()
    val favouriteItems: LiveData<List<com.example.domain.domain.entities.ItemEntity>>
        get() = _favouriteItems

    private val _items = getItemsUseCase.invoke()
    val items: LiveData<com.example.domain.domain.entities.ItemsEntity>
        get() = _items

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

    fun filterAndSort(list: List<com.example.domain.domain.entities.ItemEntity>, sortType: String, filterType: String): List<com.example.domain.domain.entities.ItemEntity> {
        val filteredList = when (filterType) {
            "Смотреть все" -> list
            "Лицо" -> list.filter { "face" in it.tags }
            "Тело" -> list.filter { "body" in it.tags }
            "Загар" -> list.filter { "suntan" in it.tags }
            "Маски" -> list.filter { "mask" in it.tags }
            else -> list
        }
        return when (sortType) {
            "По популярности" -> filteredList.sortedByDescending { it.feedback.rating }
            "По уменьшению цены" -> filteredList.sortedByDescending { it.price.priceWithDiscount.toIntOrNull() }
            "По возрастанию цены" -> filteredList.sortedBy { it.price.priceWithDiscount.toIntOrNull() }
            else -> filteredList
        }
    }
}