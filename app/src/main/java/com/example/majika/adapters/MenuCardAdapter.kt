package com.example.majika.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.cart.CartViewModel
import com.example.majika.models.Menu
import java.text.NumberFormat
import java.util.*

class MenuCardAdapter(val context: Context, private val menuList: List<Menu>, val viewModel : CartViewModel): RecyclerView.Adapter<MenuCardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var menuName: TextView
        var menuPrice: TextView
        var menuSold: TextView
        var menuDesc: TextView
        var menuQty: TextView
        var plusButton: TextView
        var minusButton: TextView

        init {
            menuName = itemView.findViewById(R.id.menuName)
            menuPrice = itemView.findViewById(R.id.menuPrice)
            menuSold = itemView.findViewById(R.id.menuSold)
            menuDesc = itemView.findViewById(R.id.menuDesc)
            menuQty = itemView.findViewById(R.id.menuQty)
            plusButton = itemView.findViewById(R.id.plusButton)
            minusButton = itemView.findViewById(R.id.minusButton)
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
        menuList[position].qty = viewModel.getQuantity(menuList[position].name)
        holder.menuName.text = menuList[position].name
        holder.menuPrice.text = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(menuList[position].price)
        holder.menuSold.text = convertToCompactFormat(menuList[position].sold) + " Terjual"
        holder.menuDesc.text = menuList[position].description
        holder.menuQty.text = menuList[position].qty.toString()
//        holder.menuQty.text = viewModel.getQuantity(menuList[position].name).toString()
        if (menuList[position].qty == 0) {
            holder.minusButton.visibility = View.GONE
        }

        holder.plusButton.setOnClickListener {
            menuList[position].qty++
            val tempInt: Int = menuList[position].qty
            holder.menuQty.text = tempInt.toString()
            holder.minusButton.visibility = View.VISIBLE



            if (tempInt == 1){
                println("Insert" + menuList[position].name + " => $tempInt ")
                viewModel.addItemToCart(menuList[position])
            }else{
                println("Update" + menuList[position].name + " => $tempInt ")
                viewModel.updateItem(menuList[position].name, tempInt)
            }
        }

        holder.minusButton.setOnClickListener {
            menuList[position].qty--
            val tempInt: Int = menuList[position].qty
            holder.menuQty.text = tempInt.toString()
            if (menuList[position].qty == 0) {
                holder.minusButton.visibility = View.GONE
            }
            viewModel.updateItem(menuList[position].name, tempInt)
        }
    }

    private fun convertToCompactFormat(number: Int): String {
        return when {
            number < 1000 -> number.toString()
            number < 1000000 -> "${number / 1000}RB+"
            number < 1000000000 -> "${number / 1000000}JT+"
            else -> "${number / 1000000000}M+"
        }
    }

}