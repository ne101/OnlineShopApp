package com.example.onlineshopapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapp.domain.entities.ItemEntity
import com.example.onlineshopapp.domain.entities.ItemsEntity
import com.example.onlineshopapp.domain.entities.PriceEntity
import com.example.onlineshopapp.domain.usecase.AddFavouriteItemUseCase
import com.example.onlineshopapp.domain.usecase.GetFavouriteItemsUseCase
import com.example.onlineshopapp.domain.usecase.GetItemsUseCase
import com.example.onlineshopapp.domain.usecase.RemoveFavouriteItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val addFavouriteItemUseCase: AddFavouriteItemUseCase,
    private val removeFavouriteItemUseCase: RemoveFavouriteItemUseCase,
    private val getFavouriteItemsUseCase: GetFavouriteItemsUseCase
) : ViewModel() {

    private val _favouriteItems = MutableLiveData<List<ItemEntity>>()
    val favouriteItems: LiveData<List<ItemEntity>>
        get() = _favouriteItems

    private val _items = getItemsUseCase.invoke()
    val items: LiveData<ItemsEntity>
        get() = _items

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

    fun filterAndSort(list: List<ItemEntity>, sortType: String, filterType: String): List<ItemEntity> {
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