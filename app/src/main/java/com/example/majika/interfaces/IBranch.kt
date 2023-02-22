package com.example.majika.interfaces

import com.example.majika.models.Branch
import com.example.majika.models.APIResponse
import retrofit2.Call
import retrofit2.http.GET

interface IBranch {
    @GET("branch")
    fun getBranch(): Call<APIResponse<Branch>>
}