package com.example.majika.fragments

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.adapters.MenuRowAdapter
import com.example.majika.cart.CartApplication
import com.example.majika.cart.CartViewModel
import com.example.majika.cart.CartViewModelFactory
import com.example.majika.models.APIResponse
import com.example.majika.models.Menu
import com.example.majika.utils.RetrofitClient
import com.example.majika.utils.Section
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.fragment.app.activityViewModels

class MenuFragment : Fragment() {
    private lateinit var adapter: MenuRowAdapter
    lateinit var mainRecyclerView: RecyclerView
    private var data: List<Section> = listOf()
    private val viewModel: CartViewModel by activityViewModels {
        CartViewModelFactory(
            (activity?.application as CartApplication).database.menuDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val search = view.findViewById<SearchView>(R.id.searchView)
        search.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var filteredData: List<Section> = data
                if (newText != null) {
                    filteredData = data.map {
                        Section(it.getHeaderTitle(), it.getItems().filter { menu ->
                            menu.name.contains(newText, true)
                        })
                    }
                }
                mainRecyclerView.adapter = MenuRowAdapter(context!!, filteredData, viewModel)
                return false
            }
        })

        mainRecyclerView = view.findViewById(R.id.recyclerview_menu_main)
        mainRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MenuRowAdapter(context!!, listOf(), viewModel)
        mainRecyclerView.adapter = adapter
        getMenu()
    }

    private fun getMenu() {
        val retrofitClient = RetrofitClient.getMenu()
        retrofitClient.getMenu().enqueue(object: Callback<APIResponse<Menu>> {
            override fun onResponse(call: Call<APIResponse<Menu>>, response: Response<APIResponse<Menu>>) {
                if (response.isSuccessful) {
                    val menuList = response.body()?.data
                    if (menuList != null) {
                        var foods: List<Menu> = listOf()
                        var drinks: List<Menu> = listOf()
                        menuList.forEach {
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
                        mainRecyclerView.adapter = MenuRowAdapter(context!!, data, viewModel)
                    } else {
                        Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<APIResponse<Menu>>, t: Throwable) {
                d("Error", t.message.toString())
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}