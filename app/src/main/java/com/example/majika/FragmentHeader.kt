package com.example.majika

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PAGE = ""

class FragmentHeader : Fragment(), SensorEventListener {
    // TODO: Rename and change types of parameters
    private var currentPage: String? = null
    private lateinit var viewModel : PageViewModel
    private var sensorManager: SensorManager? = null
    private lateinit var temperature: TextView

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
        temperature = view.findViewById<TextView>(R.id.temperature)
        sensorManager = getSystemService(requireContext(), SensorManager::class.java)

        if (sensorManager != null) {
            sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            temperature.text = "No sensor found"
        }


        viewModel.currentPage.observe(requireActivity()) {
            pageTxt.text = it.toString()
            if (pageTxt.text == "MENU") {
                temperature.visibility = View.VISIBLE
            } else {
                temperature.visibility = View.GONE
            }
        }

        return view
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        temperature.text = p0!!.values[0].toString() + "°C"
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

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