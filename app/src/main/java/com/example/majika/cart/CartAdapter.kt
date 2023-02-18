package com.example.majika.cart

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.databinding.FragmentCartBinding
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartAdapter(private val itemList:ArrayList<CartItem>, private val fragmentCartBinding: FragmentCartBinding) :
    RecyclerView.Adapter<CartAdapter.ItemViewHolder>(){



    class ItemViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.itemName)
        val count : TextView = itemView.findViewById(R.id.itemCount)
        val price : TextView = itemView.findViewById(R.id.itemPrice)
        val plusButton : Button = itemView.findViewById(R.id.plusButton)
        val minusButton : Button = itemView.findViewById(R.id.minusButton)
//        val total : TextView = itemView.findViewById(R.id.total)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_card, parent,false)
//        return view
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private fun String.toCurrencyFormat(): String {
        val localeID = Locale("in", "ID")
        val doubleValue = this.toDoubleOrNull() ?: return this
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.minimumFractionDigits = 0
        return numberFormat.format(doubleValue)
    }

    private fun getTotalPrize(): String{
        var totalPrice = 0
        itemList.forEach(){
            totalPrice += (it.count * it.price)
        }
        return  totalPrice.toString()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = itemList[position]
        holder.price.text = item.price.toString().toCurrencyFormat()
        holder.name.text = item.name
        holder.count.text= item.count.toString()


        holder.plusButton.setOnClickListener{
            item.count = item.count + 1
            holder.count.text = (item.count).toString()
            notifyItemChanged(position)

            fragmentCartBinding.total.text = getTotalPrize()
        }
//        setTotalPrice(item.price, item.count)

        holder.minusButton.setOnClickListener{
            if (item.count == 1) {
                itemList.remove(item)


                notifyItemChanged(position)
            }else{
                item.count = item.count - 1
                holder.count.text = (item.count).toString()
                notifyItemChanged(position)
            }

        }
        fragmentCartBinding.total.text = getTotalPrize()
    }
}

