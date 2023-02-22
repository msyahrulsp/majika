package com.example.majika.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface MenuDao {
    @Query("SELECT * FROM Menu")
    fun getCart(): Flow<List<Menu>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCart(menu: Menu)

    @Query("DELETE FROM Menu  WHERE id=:itemID")
    suspend fun deleteCart(itemID: Int)

    @Query("UPDATE Menu SET sold=:count WHERE id=:itemID")
    fun addQuantity(count: Int, itemID : Int)
}