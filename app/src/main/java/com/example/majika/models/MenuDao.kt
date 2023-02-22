package com.example.majika.models

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MenuDao {
    @Query("SELECT * FROM Menu")
    fun findAll(): Flow<List<Menu>>

    @Query("UPDATE Menu SET sold=:count WHERE id=:itemID")
    fun addQuantity(count: Int, itemID : Int)
}