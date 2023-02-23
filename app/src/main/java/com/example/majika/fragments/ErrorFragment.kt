package com.example.majika.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.majika.R

class ErrorFragment(main: String, desc: String) : Fragment() {
    private var mainError: String = main
    private var descError: String = desc

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val errorMain: TextView = view.findViewById(R.id.errorText)
        val errorDesc: TextView = view.findViewById(R.id.errorDesc)
        errorMain.text = mainError
        errorDesc.text = descError
    }
}