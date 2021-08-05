package com.example.shoppinglisttodo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglisttodo.domain.ShopItem
import com.example.shoppinglisttodo.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {


    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)})
    private var autoIncrementId = 0


    init {
        // добавления случайных элементов при инициализации
        for(i in 0 until 10) {
            val item = ShopItem("Name $i", i, true)
            addShopList(item)
        }
    }

    // Добавления элемента
    override fun addShopList(shopItem: ShopItem) {
        // Если элемент ещё не был определен
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
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
    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    // Обновления live data
    private fun updateList() {
        // Возвращяем копию .toList
        shopListLD.value = shopList.toList()
    }

}