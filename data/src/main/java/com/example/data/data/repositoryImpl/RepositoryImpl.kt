package com.example.data.data.repositoryImpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.data.data.api.ApiService
import com.example.data.data.database.ItemListDao
import com.example.data.data.mapper.Mapper
import com.example.domain.domain.entities.ItemEntity
import com.example.domain.domain.entities.ItemsEntity
import com.example.domain.domain.entities.PersonEntity
import com.example.domain.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: Mapper,
    private val itemListDao: ItemListDao
) : Repository {

    override fun getItems(): LiveData<ItemsEntity> {
        return liveData {
            val response = apiService.loadItems()
            emit(mapper.mapItemListToEntityList(response))
        }
    }

    override suspend fun getItem(id: String): ItemEntity {
        val items = apiService.loadItems().items
        val item = items.find { it.id == id }
        return item?.let { mapper.mapItemToEntity(it) } ?: throw Exception("Item not found")
    }

    override suspend fun getFavouriteItems(): ItemsEntity {
        val items = itemListDao.getFavouriteItemList()
        return mapper.mapItemListTableToEntity(items)
    }

    override suspend fun getFavouriteItem(id: String): ItemEntity {
        val itemWithInfo = itemListDao.getItemWithInfo(id)
        return mapper.mapItemTableToEntity(itemWithInfo)
    }

    override suspend fun getPersonInfo(): PersonEntity? {
        val person = itemListDao.getPersonInfo()
        return if (person != null) {
            mapper.mapPersonTableToEntity(person)
        } else {
            null
        }
    }

    override suspend fun addPersonInfo(personEntity: PersonEntity) {
         itemListDao.insertPersonInfo(mapper.mapPersonEntityToTable(personEntity))
    }

    override suspend fun removePersonInfo(id: Int) {
        itemListDao.deletePersonInfo(id)
    }

    override suspend fun addFavouriteItem(itemEntity: ItemEntity) {
        val itemTable = mapper.entityItemToTable(itemEntity)
        itemListDao.insertItem(itemTable)

        for (infoEntity in itemEntity.info) {
            val infoTable = mapper.mapEntityInfoToTable(infoEntity, itemEntity.id)
            itemListDao.insertInfo(infoTable)
        }
    }

    override suspend fun removeFavouriteItem(itemEntity: ItemEntity) {
        itemListDao.deleteItem(itemEntity.id)
        itemListDao.deleteInfo(itemEntity.id)
    }
}