package com.example.shoppinglisttodo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglisttodo.R
import com.example.shoppinglisttodo.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewMode: MainViewModel
    private lateinit var llShopList: LinearLayout
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Передача view model
        // two equal method
        viewMode = ViewModelProvider(this)[MainViewModel::class.java]
//        viewMode = ViewModelProvider(this).get(MainViewModel::class.java)
        llShopList = findViewById(R.id.ll_shop_list)
        viewMode.shopList.observe(this) {
            showList(it)
        }

    }
    private fun showList(list: List<ShopItem>) {
        for (shopItem in list) {
            val layoutId = if (shopItem.enable) {
                R.layout.item_shop_enabled
            } else {
                R.layout.item_shop_disabled
            }
            val view = LayoutInflater.from(this).inflate(layoutId, llShopList, false)
            llShopList.addView(view)
        }
    }

}