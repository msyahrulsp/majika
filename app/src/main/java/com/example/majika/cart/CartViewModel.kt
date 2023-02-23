package com.example.majika.cart

import android.app.Application
import androidx.lifecycle.*
import com.example.majika.models.AppDatabase
import com.example.majika.models.Menu
import com.example.majika.models.MenuDao
import com.example.majika.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CartViewModel(application : Application) : ViewModel() {

    // Cache all items form the database using LiveData.
    private val cartRepository : CartRepository = CartRepository(AppDatabase.getDatabase(application).menuDao())
    private var readAll : LiveData<List<Menu>> = cartRepository.getCart().asLiveData()

    fun getCartItems(): Flow<List<Menu>> = cartRepository.getCart()

    fun updateItem(name : String, newQuantity: Int) {
        if (newQuantity == 0){
            deleteItem(name)
        } else{
            updateQuantity(name, newQuantity)
        }
    }

    fun addItemToCart(menu: Menu){
        addItem(menu)
    }

    fun deleteAll(){
        viewModelScope.launch {
            cartRepository.deleteAll()
        }
    }
    fun getQuantity(name : String) : Int = cartRepository.findQuantity(name)
    private fun updateQuantity(name: String, qty: Int) {
        viewModelScope.launch {
            cartRepository.addQuantity(name, qty)
        }
    }
    private fun deleteItem(name: String) {
        viewModelScope.launch {
            cartRepository.deleteCart(name)
        }
    }
    private fun addItem(menu: Menu) {
        viewModelScope.launch {
            cartRepository.insertCart(menu)
        }
    }
}

class CartViewModelFactory(private  val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}