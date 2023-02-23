package com.example.majika.interfaces

import com.example.majika.models.Qr
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path


interface IQr {
    @POST("payment/{transaction_id}")
    fun getPaymentStatus(@Path("transaction_id") transcation_id: String): Call<Qr>
}