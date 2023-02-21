package com.example.majika.cart

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.databinding.FragmentCartBinding
import kotlinx.coroutines.launch

class FragmentCart : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: CartViewModel by activityViewModels {
        CartViewModelFactory(
            (activity?.application as CartApplication).database.menuDao()
        )
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        viewModel.currentCartList.observe(viewLifecycleOwner) { LiveData ->
//            CartList = LiveData as ArrayList<CartItem>
//        }
//        CartList = viewModel.currentCartList.value as ArrayList<CartItem>
        val cartAdapter = CartAdapter(binding)
        recyclerView.adapter = cartAdapter
        lifecycle.coroutineScope.launch {
            viewModel.fullSchedule().collect() {
                cartAdapter.submitList(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun chooseLayout() {
//
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        cartAdapter = CartAdapter(CartList, binding)
//        recyclerView.adapter = cartAdapter
//
//    }

}