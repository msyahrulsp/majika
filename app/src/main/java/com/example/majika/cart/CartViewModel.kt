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

fun updateItem(
    newQuantity: Int,
    id: Int
) {
    if (newQuantity == 0){
        deleteItem(id)
    } else{
        updateQuantity(newQuantity, id)
    }
}


    /**
     * Launching a new coroutine to update an item in a non-blocking way
     */
    private fun updateQuantity(count : Int, id : Int) {
        viewModelScope.launch {
            menuDao.addQuantity(count, id)
        }
    }
    private fun deleteItem(id: Int) {
        viewModelScope.launch {
            menuDao.deleteCart(id)
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