package com.example.majika.cart

import android.os.Bundle
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

        val cartAdapter = CartAdapter(binding, viewModel)

        recyclerView.adapter = cartAdapter
        lifecycle.coroutineScope.launch {
            viewModel.getCartItems().collect() {

                cartAdapter.submitList(it)
                binding.total.text = cartAdapter.getTotalPrice(it)
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}