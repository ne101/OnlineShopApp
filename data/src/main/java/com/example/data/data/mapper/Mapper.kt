package com.example.data.data.mapper

import com.example.data.data.database.*
import com.example.data.data.model.*
import com.example.domain.domain.entities.*

import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapItemToEntity(item: Item): ItemEntity = ItemEntity(
        available = item.available,
        description = item.description,
        feedback = mapFeedbackToEntity(item.feedback),
        id = item.id,
        info = item.info.map { mapInfoToEntity(it) },
        ingredients = item.ingredients,
        price = mapPriceToEntity(item.price),
        subtitle = item.subtitle,
        tags = item.tags,
        title = item.title
    )

    private fun mapFeedbackToEntity(feedback: Feedback): FeedBackEntity = FeedBackEntity(
        count = feedback.count,
        rating = feedback.rating
    )

    private fun mapPriceToEntity(price: Price): PriceEntity = PriceEntity(
        discount = price.discount,
        price = price.price,
        priceWithDiscount = price.priceWithDiscount,
        unit = price.unit
    )


    private fun mapInfoToEntity(info: Info): InfoEntity = InfoEntity(
        title = info.title,
        value = info.value
    )

    fun mapItemListToEntityList(itemResponse: ItemResponse): ItemsEntity = ItemsEntity(
        items = itemResponse.items.map { mapItemToEntity(it) }
    )

    fun mapItemTableToEntity(itemWithInfo: ItemWithInfo): ItemEntity = ItemEntity(
        available = itemWithInfo.item.available,
        description = itemWithInfo.item.description,
        feedback = itemWithInfo.item.feedback,
        id = itemWithInfo.item.id,
        info = itemWithInfo.info.map { mapInfoTableToEntity(it) },
        ingredients = itemWithInfo.item.ingredients,
        price = itemWithInfo.item.price,
        subtitle = itemWithInfo.item.subtitle,
        tags = Converters().fromString(itemWithInfo.item.tags),
        title = itemWithInfo.item.title
    )

    private fun mapInfoTableToEntity(infoTable: InfoTable): InfoEntity = InfoEntity(
        title = infoTable.title,
        value = infoTable.value
    )

    fun mapItemListTableToEntity(list: List<ItemWithInfo>): ItemsEntity = ItemsEntity(
        list.map { mapItemTableToEntity(it) }
    )

    fun entityItemToTable(itemEntity: ItemEntity): ItemTable = ItemTable(
        id = itemEntity.id,
        available = itemEntity.available,
        description = itemEntity.description,
        feedback = itemEntity.feedback,
        ingredients = itemEntity.ingredients,
        price = itemEntity.price,
        subtitle = itemEntity.subtitle,
        tags = Converters().fromList(itemEntity.tags),
        title = itemEntity.title,
    )

    fun mapEntityInfoToTable(infoEntity: InfoEntity, itemId: String): InfoTable = InfoTable(
        title = infoEntity.title,
        value = infoEntity.value,
        itemId = itemId
    )

    fun mapPersonTableToEntity(personTable: PersonTable?): PersonEntity? {
        return if (personTable != null) {
            PersonEntity(
                id = personTable.id,
                name = personTable.name,
                secondName = personTable.secondName,
                phone = personTable.phone
            )
        } else {
            null
        }
    }

    fun mapPersonEntityToTable(personEntity: PersonEntity): PersonTable = PersonTable(
        id = personEntity.id,
        name = personEntity.name,
        secondName = personEntity.secondName,
        phone = personEntity.phone
    )
}