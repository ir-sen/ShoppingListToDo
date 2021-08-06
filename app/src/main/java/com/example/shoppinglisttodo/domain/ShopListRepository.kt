package com.example.shoppinglisttodo.domain

import androidx.lifecycle.LiveData


// реализация методов UseCase для работы с ними
interface ShopListRepository {

    fun addShopList(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>


    fun deleteShopItem(shopItem: ShopItem)
}