package com.example.majika.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Menu(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @NonNull @ColumnInfo(name = "name") val name: String,
    @NonNull @ColumnInfo(name = "desc") val desc: String,
    @NonNull @ColumnInfo(name = "currency") val currency: String,
    @NonNull @ColumnInfo(name = "price") val price: Int,
    @NonNull @ColumnInfo(name = "sold") val sold: Int,
    @NonNull @ColumnInfo(name = "type") val type: String,

    )

