package com.example.majika.cart

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.databinding.FragmentCartBinding

class FragmentCart : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private val viewModel: CartViewModel by viewModels()
    private var CartList = ArrayList<CartItem>()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d("FragmentCart", "Fragmencart created!")
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        // Observe the currentScrambledWord LiveData.

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
//
        viewModel.currentCartList.observe(viewLifecycleOwner) { LiveData ->
            CartList = LiveData as ArrayList<CartItem>
        }
        CartList = viewModel.currentCartList.value as ArrayList<CartItem>
        chooseLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun chooseLayout() {

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        cartAdapter = CartAdapter(CartList, binding)
        recyclerView.adapter = cartAdapter

    }

}