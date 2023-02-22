package com.example.majika.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.majika.models.Menu
import com.example.majika.models.MenuDao
import kotlinx.coroutines.flow.Flow

class CartViewModel(private val menuDao : MenuDao) : ViewModel() {
    fun fullSchedule(): Flow<List<Menu>> = menuDao.findAll()

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