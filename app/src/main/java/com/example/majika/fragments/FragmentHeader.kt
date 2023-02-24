package com.example.majika.fragments

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.majika.viewmodel.PageViewModel
import com.example.majika.R

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

        viewModel = ViewModelProvider(requireActivity())[PageViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_header, container, false)
        val pageTxt = view.findViewById<TextView>(R.id.pageDetail)
        temperature = view.findViewById(R.id.temperature)
        sensorManager = ContextCompat.getSystemService(requireContext(), SensorManager::class.java)

        if (sensorManager != null) {
            sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
                SensorManager.SENSOR_DELAY_NORMAL
            )
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
}