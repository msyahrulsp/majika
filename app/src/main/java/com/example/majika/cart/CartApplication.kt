package com.example.majika.cart

import android.app.Application
import com.example.majika.models.AppDatabase

class CartApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}