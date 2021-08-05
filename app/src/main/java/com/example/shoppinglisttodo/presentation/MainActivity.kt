package com.example.shoppinglisttodo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglisttodo.R
import com.example.shoppinglisttodo.data.ShopListRepositoryImpl
import com.example.shoppinglisttodo.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewMode: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycleView()
        //Передача view model
        // two equal method
        viewMode = ViewModelProvider(this)[MainViewModel::class.java]
//        viewMode = ViewModelProvider(this).get(MainViewModel::class.java)
        viewMode.shopList.observe(this) {
            shopListAdapter.shopList = it
        }

    }

    private fun setupRecycleView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            // для создания определенного количество viewHolder что-бы не создавать множество viewHolder
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        // изменения данноо элемента через анонимный класс
        shopListAdapter.onShopItemLongClickListener = object : ShopListAdapter.OnShopItemLongClickListener {
            override fun onShopItemLongClick(shopItem: ShopItem) {
                viewMode.changeEnableState(shopItem)
            }

        }
    }



}