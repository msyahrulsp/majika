package com.example.majika.interfaces

import com.example.majika.models.APIResponse
import com.example.majika.models.Menu
import retrofit2.Call
import retrofit2.http.GET

interface IMenu {
    @GET("menu")
    fun getMenu(): Call<APIResponse<Menu>>

    @GET("menu/food")
    fun getFood(): Call<APIResponse<Menu>>

    @GET("menu/drink")
    fun getDrink(): Call<APIResponse<Menu>>
}