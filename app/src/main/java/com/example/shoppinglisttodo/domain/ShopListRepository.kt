package com.example.shoppinglisttodo.domain


// реализация методов UseCase для работы с ними
interface ShopListRepository {

    fun addShopList(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int): ShopItem

    fun getShopList(): List<ShopItem>

    fun deleteShopItem(shopItem: ShopItem)
}