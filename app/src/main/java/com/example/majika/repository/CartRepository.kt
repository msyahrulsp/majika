package com.example.majika.repository

import com.example.majika.models.Menu
import com.example.majika.models.MenuDao
import kotlinx.coroutines.flow.Flow

class CartRepository(private val menuDao: MenuDao) {
    fun getCart(): Flow<List<Menu>> = menuDao.getCart()

    suspend fun insertCart(menu: Menu) =
        menuDao.insertCart(menu)

    suspend fun deleteCart(itemName : String) =
        menuDao.deleteCart(itemName)

    fun addQuantity(itemName : String, count: Int) =
        menuDao.addQuantity(itemName, count)

    fun findQuantity(itemName : String) : Int =
        menuDao.findQuantity(itemName)

    suspend fun deleteAll() =
        menuDao.deleteAll()
}