package com.example.majika.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.majika.PageViewModel
import com.example.majika.R
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentQr.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentQr : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var viewModel : PageViewModel
    private lateinit var fragmentContext : Context
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var preview : Preview
    private lateinit var viewFinder : PreviewView
    private lateinit var scannerOptions : BarcodeScannerOptions
    private lateinit var scanner : BarcodeScanner
    private lateinit var qr_output : TextView
    lateinit var mainHandler: Handler

    private val scanQrTask = object : Runnable {
        override fun run() {
            scanQrCode()
            mainHandler.postDelayed(this, 500)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentContext = requireContext()

        scannerOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()

        scanner = BarcodeScanning.getClient(scannerOptions)

        viewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
        mainHandler = Handler(Looper.getMainLooper())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_qr, container, false)

        viewFinder = view.findViewById<PreviewView>(R.id.ViewFinder)
        qr_output = view.findViewById<TextView>(R.id.qr_output)

        if (viewModel.cameraPermission.value == true) {
            cameraProviderFuture = ProcessCameraProvider.getInstance(fragmentContext)
            cameraProviderFuture.addListener({
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                preview = Preview.Builder()
                    .build()
                preview.setSurfaceProvider(viewFinder.surfaceProvider)

                val cameraSelector : CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview)
                } catch (exc: Exception) {
                    Log.e("ERR", "Use case binding failed", exc)
                }
            }, ContextCompat.getMainExecutor(fragmentContext))

            cameraExecutor = Executors.newSingleThreadExecutor()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FragmentQr.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FragmentQr()
    }

    fun scanQrCode(){
        val bitmap: Bitmap = viewFinder.getBitmap() ?: Bitmap.createBitmap(
            viewFinder.width,
            viewFinder.height,
            Bitmap.Config.ARGB_8888
        )
        val image = InputImage.fromBitmap(bitmap, 0)
        val result = scanner.process(image)
            .addOnSuccessListener { barcodes ->
                // Task completed successfully
                for (barcode in barcodes) {
                    val rawValue = barcode.rawValue
                    qr_output.setText(rawValue)
                }
            }
            .addOnFailureListener {
                // Task failed with an exception
                // ...
                qr_output.setText("Failed to scan QR code")
            }
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(scanQrTask)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(scanQrTask)
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraProviderFuture.get().unbindAll()
        mainHandler.removeCallbacksAndMessages(null)
    }
}