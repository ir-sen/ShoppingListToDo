package com.example.shoppinglisttodo.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
// View model не должен зависит от data layer
import com.example.shoppinglisttodo.data.ShopListRepositoryImpl
import com.example.shoppinglisttodo.domain.*

class MainViewModel: ViewModel() {

    // неправельное присвоение репозитория
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

        // Взаимодействия с live data
    val shopList = getShopListUseCase.getShopList()

    // получаем список и устанавливаем в нашу Live data


    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enable = !shopItem.enable)
        editShopItemUseCase.editShopItem(newItem)
    }


}