package com.example.majika.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.databinding.CartCardBinding
import com.example.majika.databinding.FragmentCartBinding
import com.example.majika.models.Menu
import java.text.NumberFormat
import java.util.*


class CartAdapter(
    private val fragmentCartBinding: FragmentCartBinding,
    private val viewModel : CartViewModel
    ) : ListAdapter<Menu, CartAdapter.ItemViewHolder>(DiffCallback) {


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

    class ItemViewHolder( private var binding: CartCardBinding, var viewModel: CartViewModel) : RecyclerView.ViewHolder(binding.root) {
//        @SuppressLint("SimpleDateFormat")


        fun bind(menu: Menu) {
            binding.itemName.text = menu.name
            binding.itemCount.text = menu.sold.toString()
            binding.itemPrice.text = menu.price.toString().toCurrencyFormat()
            binding.plusButton.setOnClickListener{ viewModel.updateItem(menu.sold + 1, menu.id) }
            binding.minusButton.setOnClickListener{ viewModel.updateItem(menu.sold - 1, menu.id) }
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
            ),
            viewModel
        )
    }



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getTotalPrice(menu: List<Menu>) : String{
        var sum = 0

        menu.forEach{
            sum += it.price * it.sold
        }

        val localeID = Locale("in", "ID")
        val doubleValue = sum.toString().toDoubleOrNull() ?: return sum.toString()
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.minimumFractionDigits = 0
        return numberFormat.format(doubleValue)
    }

}

