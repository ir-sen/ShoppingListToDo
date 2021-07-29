package com.example.shoppinglisttodo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglisttodo.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewMode: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Передача view model
        // two equal method
        viewMode = ViewModelProvider(this)[MainViewModel::class.java]
//        viewMode = ViewModelProvider(this).get(MainViewModel::class.java)
        viewMode.shopList.observe(this) {
            Log.d("MainViewModelTest",  it.toString())
        }
        viewMode.getShopList()

    }
}