package com.example.onlineshopapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapp.domain.entities.ItemEntity
import com.example.onlineshopapp.domain.entities.ItemsEntity
import com.example.onlineshopapp.domain.entities.PersonEntity
import com.example.onlineshopapp.domain.usecase.GetFavouriteItemsUseCase
import com.example.onlineshopapp.domain.usecase.GetPersonInfoUseCase
import com.example.onlineshopapp.domain.usecase.RemoveFavouriteItemUseCase
import com.example.onlineshopapp.domain.usecase.RemovePersonInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getPersonInfoUseCase: GetPersonInfoUseCase,
    private val removePersonInfoUseCase: RemovePersonInfoUseCase,
    private val getFavouriteItemsUseCase: GetFavouriteItemsUseCase,
    private val removeFavouriteItemUseCase: RemoveFavouriteItemUseCase
) : ViewModel() {

    private val _person = MutableLiveData<PersonEntity>()
    val person: LiveData<PersonEntity>
        get() = _person
    private val _favouriteItems = MutableLiveData<ItemsEntity>()
    val favouriteItems: LiveData<ItemsEntity>
        get() = _favouriteItems

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _person.postValue(getPersonInfoUseCase.invoke())
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke())
        }
    }

    fun removePersonInfo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            removePersonInfoUseCase.invoke(id)
        }
    }

    fun removeItemFromDB(item: ItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavouriteItemUseCase.invoke(item)
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke())
        }
    }
}