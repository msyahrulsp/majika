package com.example.majika

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PAGE = ""

class FragmentHeader : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_header, container, false)
        val pageTxt = view.findViewById<TextView>(R.id.pageDetail)

        viewModel.currentPage.observe(requireActivity()) {
            pageTxt.text = it.toString()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param currentPage ARG_PAGE
         * @return A new instance of fragment FragmentHeader.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(currentPage: String) =
            FragmentHeader().apply {
                arguments = Bundle().apply {
                    putString(ARG_PAGE, currentPage)
                }
            }
    }
}