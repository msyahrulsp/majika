package com.example.majika.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface MenuDao {
    @Query("SELECT * FROM Menu")
    fun getCart(): Flow<List<Menu>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCart(menu: Menu)

    @Query("DELETE FROM Menu  WHERE name=:itemName")
    suspend fun deleteCart(itemName : String)

    @Query("UPDATE Menu SET qty=:count WHERE name=:itemName")
    fun addQuantity(itemName : String, count: Int)

    @Query("SELECT qty FROM Menu WHERE name=:itemName")
    fun findQuantity(itemName : String) : Int

    @Query("DELETE FROM Menu")
    suspend fun deleteAll()
}