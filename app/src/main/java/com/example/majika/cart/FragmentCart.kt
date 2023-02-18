package com.example.majika

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.cart.CartItem
import com.example.majika.databinding.FragmentCartBinding

class FragmentCart : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        chooseLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun chooseLayout() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemList = ArrayList<CartItem>()
        itemList.add(CartItem("Item 1", 50000,5))
        itemList.add(CartItem("Item 2", 50000,1))
//        itemList.add(CartItem("Item 3", 50000,5))
//        itemList.add(CartItem("Item 4", 50000,5))
//        itemList.add(CartItem("item 5", 50000,5))
//        itemList.add(CartItem("item 6", 50000,6))

        recyclerView.adapter = CartAdapter(itemList)
    }
}