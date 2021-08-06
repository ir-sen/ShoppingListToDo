package com.example.shoppinglisttodo.presentation

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
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
            shopListAdapter.submitList(it)
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
        setupLongClickListener()
        setupClickListener()

        setupSwipeListener(rvShopList)
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // получения элемента который мы свайпнули
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewMode.deleteShopItem(item)

            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            Log.d("Click", it.toString())
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewMode.changeEnableState(it)
        }
    }


}