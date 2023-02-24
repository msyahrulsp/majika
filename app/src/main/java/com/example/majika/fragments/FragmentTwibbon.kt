package com.example.majika.fragments

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.majika.viewmodel.PageViewModel
import com.example.majika.R
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentTwibbon.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_CAM_FREEZE = "false"

class FragmentTwibbon : Fragment() {
    // TODO: Rename and change types of parameters
    private var cameraFreeze : Boolean = false
    private lateinit var viewModel : PageViewModel
    private lateinit var testContext : Context
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var preview : Preview
    private lateinit var viewFinder : PreviewView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testContext = requireContext()

        viewModel = ViewModelProvider(requireActivity())[PageViewModel::class.java]

        arguments?.let {
              cameraFreeze = it.getBoolean(ARG_CAM_FREEZE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cameraFreeze = false

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_twibbon, container, false)

        // set preview to the viewfinder
        viewFinder = view.findViewById(R.id.ViewFinder)
        val imagePreview = view.findViewById<ImageView>(R.id.ImagePreview)
        imagePreview.visibility = View.GONE
        val camBtn = view.findViewById<Button>(R.id.CaptureButton)
        camBtn.setOnClickListener{
            if (cameraFreeze) {
                cameraFreeze = false
                imagePreview.visibility = View.GONE
                viewFinder.visibility = View.VISIBLE
                camBtn.text = "Capture"
            }
            else {
                cameraFreeze = true
                imagePreview.visibility = View.VISIBLE
                viewFinder.visibility = View.GONE
                camBtn.text = "Retake"
                imagePreview.background = BitmapDrawable(resources, viewFinder.bitmap)
            }
        }


        if (viewModel.cameraPermission.value == true) {
            cameraProviderFuture = ProcessCameraProvider.getInstance(testContext)
            cameraProviderFuture.addListener({
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                preview = Preview.Builder()
                    .build()
                preview.setSurfaceProvider(viewFinder.surfaceProvider)

                val cameraSelector : CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview)
                } catch (exc: Exception) {
                    Log.e("ERR", "Use case binding failed", exc)
                }
            }, ContextCompat.getMainExecutor(testContext))

            cameraExecutor = Executors.newSingleThreadExecutor()
        }

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param cameraFreeze Parameter 1.
         * @return A new instance of fragment FragmentTwibbon.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(cameraFreeze: Boolean) =
            FragmentTwibbon().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_CAM_FREEZE, cameraFreeze)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraProviderFuture.get().unbindAll()
    }


}