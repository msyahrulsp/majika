package com.example.majika.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.models.Menu
import java.text.NumberFormat
import java.util.*

class MenuCardAdapter(val context: Context, val menuList: List<Menu>): RecyclerView.Adapter<MenuCardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var menuName: TextView
        var menuPrice: TextView
        var menuSold: TextView
        var menuDesc: TextView
        var menuQty: TextView
        var qty: Int

        init {
            menuName = itemView.findViewById(R.id.menuName)
            menuPrice = itemView.findViewById(R.id.menuPrice)
            menuSold = itemView.findViewById(R.id.menuSold)
            menuDesc = itemView.findViewById(R.id.menuDesc)
            menuQty = itemView.findViewById(R.id.menuQty)
            qty = 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.menu_card, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.menuName.text = menuList[position].name
        holder.menuPrice.text = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(menuList[position].price)
        holder.menuSold.text = menuList[position].sold.toString()
        holder.menuDesc.text = menuList[position].description
        holder.menuQty.text = holder.qty.toString()
    }

}