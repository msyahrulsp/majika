package com.example.majika.cart

import androidx.lifecycle.*
import com.example.majika.models.Menu
import com.example.majika.models.MenuDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CartViewModel(private val menuDao : MenuDao) : ViewModel() {

    // Cache all items form the database using LiveData.
    val allItems: LiveData<List<Menu>> = menuDao.getCart().asLiveData()

    fun getCartItems(): Flow<List<Menu>> = menuDao.getCart()

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
            menuDao.deleteAll()
        }
    }
    fun getQuantity(name : String) : Int = menuDao.findQuantity(name)
    private fun updateQuantity(name: String, qty: Int) {
        viewModelScope.launch {
            menuDao.addQuantity(name, qty)
        }
    }
    private fun deleteItem(name: String) {
        viewModelScope.launch {
            menuDao.deleteCart(name)
        }
    }
    private fun addItem(menu: Menu) {
        viewModelScope.launch {
            menuDao.insertCart(menu)
        }
    }
}

class CartViewModelFactory(private  val menuDao : MenuDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(menuDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}