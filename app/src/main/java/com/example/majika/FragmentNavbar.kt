package com.example.majika

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.example.majika.cart.FragmentCart
import com.example.majika.fragments.BranchFragment
import com.example.majika.fragments.MenuFragment
import com.example.majika.fragments.FragmentTwibbon

private const val ARG_PAGE = ""

class FragmentNavbar : Fragment() {
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

        if (currentPage == null) {
            currentPage = Page.CART.page
            updateViewModelPage(Page.CART.page)
            cartBtn.setImageResource(R.drawable.cart_active)
            replaceFragment(FragmentCart())
        }

        camBtn.setOnClickListener{
            if (currentPage != Page.CAMERA.page){
                currentPage = Page.CAMERA.page
                updateViewModelPage(Page.CAMERA.page)
                resetNavbarIcons()
                camBtn.setImageResource(R.drawable.camera_active)
                replaceFragment(FragmentTwibbon())
            }
        }
        locBtn.setOnClickListener{
            if (currentPage != Page.LOCATION.page){
                currentPage = Page.LOCATION.page
                updateViewModelPage(Page.LOCATION.page)
                resetNavbarIcons()
                locBtn.setImageResource(R.drawable.location_active)
                replaceFragment(BranchFragment())
            }
        }
        restaurantBtn.setOnClickListener{
            if (currentPage != Page.RESTAURANT.page){
                currentPage = Page.RESTAURANT.page
                updateViewModelPage(Page.RESTAURANT.page)
                resetNavbarIcons()
                restaurantBtn.setImageResource(R.drawable.restaurant_active)
                replaceFragment(MenuFragment())
            }
        }
        cartBtn.setOnClickListener{
            if (currentPage != Page.CART.page){
                currentPage = Page.CART.page
                updateViewModelPage(Page.CART.page)
                resetNavbarIcons()
                cartBtn.setImageResource(R.drawable.cart_active)
                replaceFragment(FragmentCart())
            }
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private enum class Page(val page: String) {
        CAMERA("TWIBBON"), LOCATION("CABANG RESTORAN"), RESTAURANT("MENU"), CART("KERANJANG")
    }
}