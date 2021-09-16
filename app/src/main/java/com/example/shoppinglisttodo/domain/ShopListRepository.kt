package com.example.shoppinglisttodo.domain

import androidx.lifecycle.LiveData


// реализация методов UseCase для работы с ними
interface ShopListRepository {

    suspend fun addShopList(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(shopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>


    suspend fun deleteShopItem(shopItem: ShopItem)
}