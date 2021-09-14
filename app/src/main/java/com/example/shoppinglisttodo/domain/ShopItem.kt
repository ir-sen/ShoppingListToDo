package com.example.shoppinglisttodo.domain



data class ShopItem(
    val name: String,
    val count: Int,
    val enable: Boolean,
    var id: Int = UNDEFINED_ID) {

    companion object {
        // Метка не определенного id
        const val UNDEFINED_ID = -1
    }

}
