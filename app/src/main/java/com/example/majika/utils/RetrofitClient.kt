package com.example.majika.utils

import com.example.majika.interfaces.IBranch
import com.example.majika.interfaces.IMenu
import com.example.majika.interfaces.IQr
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private const val BASE_URL = "http://pbd-nox.ddns.net/v1/"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun getData(): IBranch {
            return retrofit.create(IBranch::class.java)
        }

        fun getBranch(): IBranch {
            return retrofit.create(IBranch::class.java)
        }

        fun getMenu(): IMenu {
            return retrofit.create(IMenu::class.java)
        }

        fun getPaymentStatus(): IQr{
            return retrofit.create(IQr::class.java)
        }
    }
}