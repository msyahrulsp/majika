package com.example.majika.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.cart.CartViewModel
import com.example.majika.models.Menu
import com.example.majika.utils.Section

class MenuRowAdapter(val context: Context, private val sectionList: List<Section>, val viewModel : CartViewModel): RecyclerView.Adapter<MenuRowAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var foodType: TextView
        var recyclerView: RecyclerView

        init {
            foodType = itemView.findViewById(R.id.food_type)
            recyclerView = itemView.findViewById(R.id.recyclerview_menu_row)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.menu_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return sectionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.foodType.text = sectionList[position].getHeaderTitle()
        val section: Section = sectionList[position]
        val items: List<Menu> = section.getItems()

        val menuCardAdapter = MenuCardAdapter(context, items, viewModel)
        holder.recyclerView.layoutManager = LinearLayoutManager(context)
        holder.recyclerView.adapter = menuCardAdapter
    }

}