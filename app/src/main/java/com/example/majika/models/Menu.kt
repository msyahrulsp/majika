package com.example.majika.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Menu(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "sold") val sold: Int,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "qty") var qty: Int = 0,
    )

