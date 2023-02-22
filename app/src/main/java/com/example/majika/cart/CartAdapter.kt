package com.example.majika.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.majika.databinding.CartCardBinding
import com.example.majika.databinding.FragmentCartBinding
import com.example.majika.models.Menu
import java.text.NumberFormat
import java.util.*

class CartAdapter(private val fragmentCartBinding: FragmentCartBinding) : ListAdapter<Menu, CartAdapter.ItemViewHolder>(DiffCallback) {
//class CartAdapter(private val itemList:ArrayList<CartItem>, private val fragmentCartBinding: FragmentCartBinding) :
//    RecyclerView.Adapter<CartAdapter.ItemViewHolder>(){


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ItemViewHolder( private var binding: CartCardBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        val plusButton: Button = binding.plusButton
        val minusButton: Button = binding.minusButton
        val count : TextView = binding.itemCount

        fun bind(menu: Menu) {
            binding.itemName.text = menu.name
            binding.itemCount.text = menu.sold.toString()
            binding.itemPrice.text = menu.price.toString().toCurrencyFormat()
        }

        private fun String.toCurrencyFormat(): String {
            val localeID = Locale("in", "ID")
            val doubleValue = this.toDoubleOrNull() ?: return this
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            numberFormat.minimumFractionDigits = 0
            return numberFormat.format(doubleValue)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            CartCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))


        holder.plusButton.setOnClickListener{

            holder.count.text = (Integer.parseInt(holder.count.text as String) + 1).toString()

            notifyItemChanged(position)

//            fragmentCartBinding.total.text = getTotalPrize()
        }
//        setTotalPrice(item.price, item.count)

        holder.minusButton.setOnClickListener{
            try{
                if (holder.count.text.equals('1')) {
//                    itemList.remove(item)

//                    fragmentCartBinding.total.text = getTotalPrize()
//                    notifyDataSetChanged()

                }else{
                    holder.count.text = (Integer.parseInt(holder.count.text as String) - 1).toString()
//                    holder.count.text = (item.count).toString()
//                    fragmentCartBinding.total.text = getTotalPrize()
                    notifyItemChanged(position)
                }


            } catch (e : Exception){
                println("ERROR>>>$e")
            }


        }
//        fragmentCartBinding.total.text = getTotalPrize()
    }
}

