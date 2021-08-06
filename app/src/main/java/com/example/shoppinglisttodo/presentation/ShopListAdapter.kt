package com.example.shoppinglisttodo.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglisttodo.R
import com.example.shoppinglisttodo.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var count = 0
    var shopList = listOf<ShopItem>()
        // set кода мы устанавливаем новое значения
    set(value) {
        // для сравнения старого и нового списка
        val callback = ShopListDiffCallback(shopList, value)
            // Метод вычесления что измменилось (производит вычесления)
        val diffResult = DiffUtil.calculateDiff(callback)
            // Передача действий адаптеру
        diffResult.dispatchUpdatesTo(this)
        field = value
    }
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
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    // присваеваем к каждому элементу значения
    // (Вызывается для каждого элемента)
    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        Log.d("ShopListAdapter", "onBindViewHolder count = ${++count}")
        val shopItem = shopList[position]
        // слушатель при полгом нажатии
        viewHolder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        viewHolder.view.setOnClickListener{
            onShopItemClickListener?.invoke(shopItem)
        }

        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()

    }
    // вызывается в момен переиспользования  View holder
    override fun onViewRecycled(viewHolder: ShopItemViewHolder) {
        super.onViewRecycled(viewHolder)
        viewHolder.tvName.text = ""
        viewHolder.tvCount.text = ""
        viewHolder.tvName.setTextColor(ContextCompat.getColor(viewHolder.view.context, android.R.color.white))
    }

    // second way fix scroll bug
// определяет тип view
    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.enable) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }


    override fun getItemCount(): Int {
        return shopList.size
    }
    // Берем все view которые нам нужны
    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)

    }

    // для установления слушателя на кнопку
    interface OnShopItemLongClickListener{
        fun onShopItemLongClick(shopItem: ShopItem)
    }

    interface OnClickItemListener{
        fun onClickItemListener(shopItem: ShopItem)
    }

    companion object{

        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        //View holder max create
        const val MAX_POOL_SIZE = 15
    }
}