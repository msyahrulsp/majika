package com.example.majika.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.FragmentNavbar
import com.example.majika.R
import com.example.majika.adapters.CartAdapter
import com.example.majika.cart.CartApplication
import com.example.majika.cart.CartViewModel
import com.example.majika.cart.CartViewModelFactory
import com.example.majika.databinding.FragmentCartBinding
import kotlinx.coroutines.launch

class FragmentCart(private val fragmentNavbar: FragmentNavbar) : Fragment() {
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

        val cartAdapter = CartAdapter(viewModel)

        recyclerView.adapter = cartAdapter
        lifecycle.coroutineScope.launch {
            viewModel.getCartItems().collect {

                cartAdapter.submitList(it)
                binding.total.text = cartAdapter.getTotalPrice(it)
            }
        }

        binding.bayarButton.setOnClickListener {
            replaceFragment(FragmentQr(binding.total.text as String, fragmentNavbar))
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}