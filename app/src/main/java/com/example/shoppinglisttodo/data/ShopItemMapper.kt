package com.example.shoppinglisttodo.data

import com.example.shoppinglisttodo.domain.ShopItem

class ShopItemMapper {

    // Приобразования одной сущности в другую
    fun mapEntityToDbModel(shopItem: ShopItem): ShopItemDbModel {
        return ShopItemDbModel(
            id = shopItem.id,
            name = shopItem.name,
            count = shopItem.count,
            enable = shopItem.enable
        )
    }

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel): ShopItem {
        return ShopItem(
            id = shopItemDbModel.id,
            name = shopItemDbModel.name,
            count = shopItemDbModel.count,
            enable = shopItemDbModel.enable
        )
    }


    // приобразования в List объектов ShopItemDbModel
    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) =
        list.map {
            mapDbModelToEntity(it)
        }


}