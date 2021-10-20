package com.example.shoppinglisttodo.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglisttodo.R
import com.example.shoppinglisttodo.databinding.ItemShopDisabledBinding
import com.example.shoppinglisttodo.databinding.ItemShopEnabledBinding
import com.example.shoppinglisttodo.domain.ShopItem

class ShopListAdapter: androidx.recyclerview.widget.ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

//    var shopList = listOf<ShopItem>()
//        // set кода мы устанавливаем новое значения
//    set(value) {
//        // для сравнения старого и нового списка
//        val callback = ShopListDiffCallback(shopList, value)
//            // Метод вычесления что измменилось (производит вычесления)
//        val diffResult = DiffUtil.calculateDiff(callback)
//            // Передача действий адаптеру
//        diffResult.dispatchUpdatesTo(this)
//        field = value
//    }
     // организация OnShop Click Listener как на java
    //var onShopItemLongClickListener: OnShopItemLongClickListener? = null
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    // из layout получаем view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when(viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    // присваеваем к каждому элементу значения
    // (Вызывается для каждого элемента)
    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
//        val shopItem = shopList[position]
        val shopItem = getItem(position) // в ListAdapter мы вызываем так
        val binding = viewHolder.binding
        // слушатель при полгом нажатии
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        binding.root.setOnClickListener{
            onShopItemClickListener?.invoke(shopItem)
        }

        when (binding) {
            is ItemShopDisabledBinding -> {
                binding.shopItem = shopItem
            }

            is ItemShopEnabledBinding -> {
                binding.shopItem = shopItem
            }
        }


    }
    // вызывается в момен переиспользования  View holder


    // second way fix scroll bug
// определяет тип view
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) // передаем как в ListAdapter
        return if (item.enable) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }


//    override fun getItemCount(): Int {
//        return shopList.size
//    }
    // Берем все view которые нам нужны


    // для установления слушателя на кнопку
//    interface OnShopItemLongClickListener{
//        fun onShopItemLongClick(shopItem: ShopItem)
//    }
//
//    interface OnClickItemListener{
//        fun onClickItemListener(shopItem: ShopItem)
//    }

    companion object{

        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        //View holder max create
        const val MAX_POOL_SIZE = 15
    }
}