package com.example.majika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.majika.databinding.ActivityMainBinding
import com.example.majika.models.APIResponse
import com.example.majika.models.Menu
import com.example.majika.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: PageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        // ini cuman temporary, ngetest data duang
        getDataTemp()
    }

    private fun getDataTemp() {
        // Call retrofit class
        val retrofitClient = RetrofitClient.getMenu()

        retrofitClient.getMenu().enqueue(object : Callback<APIResponse<Menu>> {
            override fun onResponse(call: Call<APIResponse<Menu>>, response: Response<APIResponse<Menu>>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("Data", data.toString())
                }
            }

            override fun onFailure(call: Call<APIResponse<Menu>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }
}