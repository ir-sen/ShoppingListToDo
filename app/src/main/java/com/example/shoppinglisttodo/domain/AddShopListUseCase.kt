package com.example.shoppinglisttodo.domain

class AddShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun addShopList(shopItem: ShopItem) {
        shopListRepository.addShopList(shopItem)
    }
}