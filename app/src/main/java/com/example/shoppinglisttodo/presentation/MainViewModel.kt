package com.example.shoppinglisttodo.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// View model не должен зависит от data layer
import com.example.shoppinglisttodo.data.ShopListRepositoryImpl
import com.example.shoppinglisttodo.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    // неправельное присвоение репозитория
    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)


        // Взаимодействия с live data
    val shopList = getShopListUseCase.getShopList()

    // получаем список и устанавливаем в нашу Live data


    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val newItem = shopItem.copy(enable = !shopItem.enable)
            editShopItemUseCase.editShopItem(newItem)
        }
    }


}