package com.example.majika

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.majika.cart.FragmentCart
import com.example.majika.fragments.BranchFragment
import com.example.majika.fragments.MenuFragment
import com.example.majika.fragments.FragmentTwibbon

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PAGE = ""

class FragmentNavbar : Fragment() {
    // TODO: Rename and change types of parameters
    private var currentPage: String? = null
    private lateinit var viewModel : PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentPage = it.getString(ARG_PAGE)
        }

        viewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
    }

    private fun updateViewModelPage(page: String){
        viewModel.currentPage.value = page
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_navbar, container, false)

        // add actions for when the user pressed the icons
        val camBtn = view.findViewById<ImageButton>(R.id.CameraButton)
        val locBtn = view.findViewById<ImageButton>(R.id.LocationButton)
        val restaurantBtn = view.findViewById<ImageButton>(R.id.RestaurantButton)
        val cartBtn = view.findViewById<ImageButton>(R.id.CartButton)

        fun resetNavbarIcons() {
            camBtn.setImageResource(R.drawable.camera)
            locBtn.setImageResource(R.drawable.location)
            restaurantBtn.setImageResource(R.drawable.restaurant)
            cartBtn.setImageResource(R.drawable.cart)
        }

        currentPage = "CART"
        updateViewModelPage("CART")
        cartBtn.setImageResource(R.drawable.cart_active)
        replaceFragment(FragmentCart())

        camBtn.setOnClickListener{
            if (currentPage != "CAMERA"){
                currentPage = "CAMERA"
                updateViewModelPage("CAMERA")
                resetNavbarIcons()
                camBtn.setImageResource(R.drawable.camera_active)
                replaceFragment(FragmentTwibbon())
            }
        }
        locBtn.setOnClickListener{
            if (currentPage != "LOCATION"){
                currentPage = "LOCATION"
                updateViewModelPage("LOCATION")
                resetNavbarIcons()
                locBtn.setImageResource(R.drawable.location_active)
                replaceFragment(BranchFragment())
            }
        }
        restaurantBtn.setOnClickListener{
            if (currentPage != "MENU"){
                currentPage = "MENU"
                updateViewModelPage("MENU")
                resetNavbarIcons()
                restaurantBtn.setImageResource(R.drawable.restaurant_active)
                replaceFragment(MenuFragment())
            }
        }
        cartBtn.setOnClickListener{
            if (currentPage != "CART"){
                currentPage = "CART"
                updateViewModelPage("CART")
                resetNavbarIcons()
                cartBtn.setImageResource(R.drawable.cart_active)
                replaceFragment(FragmentCart())
            }
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param currentPage parameter_page.
         * @return A new instance of fragment FragmentNavbar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(currentPage: String) =
            FragmentNavbar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PAGE, currentPage)
                }
            }
    }
}