package com.example.onlineshopapp.data.database

import androidx.room.*

@Dao
interface ItemListDao {

    @Transaction
    @Query("SELECT * FROM favourite_item_table")
    suspend fun getFavouriteItemList(): List<ItemWithInfo>

    @Transaction
    @Query("SELECT * FROM favourite_item_table WHERE id=:id")
    fun getItem(id: String): ItemWithInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemTable: ItemTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(infoTable: InfoTable)

    @Query("DELETE FROM favourite_item_table WHERE id=:id")
    suspend fun deleteItem(id: String)

    @Query("DELETE FROM info_table WHERE itemId=:id")
    suspend fun deleteInfo(id: String)

    @Transaction
    @Query("SELECT * FROM favourite_item_table WHERE id=:id")
    suspend fun getItemWithInfo(id: String): ItemWithInfo

    @Query("SELECT * FROM person_table")
    suspend fun getPersonInfo(): PersonTable?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonInfo(personTable: PersonTable)

    @Query("DELETE FROM person_table WHERE id=:id")
    suspend fun deletePersonInfo(id: Int)
}