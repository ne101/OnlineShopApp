package com.example.onlineshopapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.domain.entities.ItemEntity
import com.example.domain.domain.entities.ItemsEntity
import com.example.domain.domain.entities.PersonEntity
import com.example.domain.domain.usecase.GetFavouriteItemsUseCase
import com.example.domain.domain.usecase.GetPersonInfoUseCase
import com.example.domain.domain.usecase.RemoveFavouriteItemUseCase
import com.example.domain.domain.usecase.RemovePersonInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getPersonInfoUseCase: com.example.domain.domain.usecase.GetPersonInfoUseCase,
    private val removePersonInfoUseCase: com.example.domain.domain.usecase.RemovePersonInfoUseCase,
    private val getFavouriteItemsUseCase: com.example.domain.domain.usecase.GetFavouriteItemsUseCase,
    private val removeFavouriteItemUseCase: com.example.domain.domain.usecase.RemoveFavouriteItemUseCase
) : ViewModel() {

    private val _person = MutableLiveData<com.example.domain.domain.entities.PersonEntity>()
    val person: LiveData<com.example.domain.domain.entities.PersonEntity>
        get() = _person
    private val _favouriteItems = MutableLiveData<com.example.domain.domain.entities.ItemsEntity>()
    val favouriteItems: LiveData<com.example.domain.domain.entities.ItemsEntity>
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

    fun removeItemFromDB(item: com.example.domain.domain.entities.ItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavouriteItemUseCase.invoke(item)
            _favouriteItems.postValue(getFavouriteItemsUseCase.invoke())
        }
    }
}