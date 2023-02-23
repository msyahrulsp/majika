package com.example.majika.fragments

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.adapters.MenuRowAdapter
import com.example.majika.models.APIResponse
import com.example.majika.models.Menu
import com.example.majika.utils.RetrofitClient
import com.example.majika.utils.Section
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuFragment : Fragment() {
    lateinit var adapter: MenuRowAdapter
    lateinit var mainRecyclerView: RecyclerView
    private var data: List<Section> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainRecyclerView = view.findViewById(R.id.recyclerview_menu_main)
        mainRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MenuRowAdapter(context!!, listOf())
        mainRecyclerView.adapter = adapter
        getMenu()
    }

    private fun getMenu() {
        val retrofitClient = RetrofitClient.getMenu()
        retrofitClient.getMenu().enqueue(object: Callback<APIResponse<Menu>> {
            override fun onResponse(call: Call<APIResponse<Menu>>, response: Response<APIResponse<Menu>>) {
                if (response.isSuccessful) {
                    var foods: List<Menu> = listOf()
                    var drinks: List<Menu> = listOf()
                    val menuList = response.body()?.data
                    menuList?.forEach {
                        if (it.type == "Food") {
                            foods += it
                        } else {
                            drinks += it
                        }
                    }
                    data = listOf(
                        Section("Foods", foods),
                        Section("Drinks", drinks)
                    )
                    mainRecyclerView.adapter = MenuRowAdapter(context!!, data)
                    d("Data", data.toString())
                }
            }

            override fun onFailure(call: Call<APIResponse<Menu>>, t: Throwable) {
                d("Error", t.message.toString())
                val list: List<Section> = listOf()
                mainRecyclerView.adapter = MenuRowAdapter(context!!, list)
            }
        })
    }
}