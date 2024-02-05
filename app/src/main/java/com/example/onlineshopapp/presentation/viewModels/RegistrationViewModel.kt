package com.example.onlineshopapp.presentation.viewModels

import android.graphics.Color
import android.widget.EditText
import androidx.lifecycle.*
import com.example.onlineshopapp.domain.entities.PersonEntity
import com.example.onlineshopapp.domain.usecase.AddPersonInfoUseCase
import com.example.onlineshopapp.domain.usecase.GetPersonInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val addPersonInfoUseCase: AddPersonInfoUseCase,
    private val getPersonInfoUseCase: GetPersonInfoUseCase
) : ViewModel() {

    private val _nameValid = MutableLiveData<Boolean>()

    private val _secondNameValid = MutableLiveData<Boolean>()

    private val _phoneValid = MutableLiveData<Boolean>()

    private val _person = MutableLiveData<PersonEntity>()
    val person: LiveData<PersonEntity>
        get() = _person

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _secondName = MutableLiveData<String>()
    val secondName: LiveData<String>
        get() = _secondName

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String>
        get() = _phone

    val allValid: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(_nameValid) { value = checkAllValid() }
        addSource(_secondNameValid) { value = checkAllValid() }
        addSource(_phoneValid) { value = checkAllValid() }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _person.postValue(getPersonInfoUseCase.invoke())
        }
    }

    private fun checkAllValid(): Boolean {
        return _nameValid.value == true && _secondNameValid.value == true && _phoneValid.value == true
    }

    fun checkNameValid(etName: EditText) {
        val name = etName.text.toString()
        if (name.matches(Regex("[а-яА-ЯёЁ]*"))) {
            etName.setBackgroundColor(Color.parseColor("#F8F8F8"))
            _nameValid.value = true
            _name.value = name
        } else {
            etName.setBackgroundColor(Color.parseColor("#B3FF0000"))
            _nameValid.value = false
        }
    }

    fun addPerson(personEntity: PersonEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            addPersonInfoUseCase.invoke(personEntity)
        }
    }

    fun checkDelete(et: EditText): Boolean {
        val input = et.text.toString().trim()
        return input.isNotEmpty()
    }

    fun checkSecondNameValid(etSecondName: EditText) {
        val secondName = etSecondName.text.toString()
        if (secondName.matches(Regex("[а-яА-ЯёЁ]*"))) {
            etSecondName.setBackgroundColor(Color.parseColor("#F8F8F8"))
            _secondNameValid.value = true
            _secondName.value = secondName
        } else {
            etSecondName.setBackgroundColor(Color.parseColor("#B3FF0000"))
            _secondNameValid.value = false
        }
    }

    fun checkNumberValid(etNumber: EditText) {
        val str = "+7 ([000]) [000]-[00]-[00]"
        val number = etNumber.text.toString()
        if (number.length == str.length - 8) {
            etNumber.setBackgroundColor(Color.parseColor("#F8F8F8"))
            _phoneValid.value = true
            _phone.value = number
        } else {
            etNumber.setBackgroundColor(Color.parseColor("#B3FF0000"))
            _phoneValid.value = false
        }
    }

}