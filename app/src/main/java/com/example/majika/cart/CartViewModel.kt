package com.example.majika.cart

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {

    private val _cartList = ArrayList<CartItem>()
    private val _cartListLiveData = MutableLiveData<MutableList<CartItem>>()


    val currentCartList: MutableLiveData<MutableList<CartItem>>
        get() = _cartListLiveData

    init {
        addCartItem(CartItem("Sushi", 3000, 1))
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private fun addCartItem(cartItem: CartItem) {
        _cartList.add(cartItem)
        _cartListLiveData.value = _cartList
        _cartListLiveData.notifyObserver()
    }
}