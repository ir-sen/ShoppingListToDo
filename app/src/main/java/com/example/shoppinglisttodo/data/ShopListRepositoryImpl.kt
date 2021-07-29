package com.example.shoppinglisttodo.data

import com.example.shoppinglisttodo.domain.ShopItem
import com.example.shoppinglisttodo.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    // Добавления элемента
    override fun addShopList(shopItem: ShopItem) {
        // Если элемент ещё не был определен
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    // удаления старого объекта и замена его на новый
    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id) // назодим элемент старый по id
        shopList.remove(oldElement) // Удлаляем
        addShopList(shopItem) // Добавляем новый
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found")
    }
    // Возвращяем копию листа что бы не было доступа из других классов
    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

}