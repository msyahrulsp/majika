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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.majika.FragmentNavbar
import com.example.majika.PageViewModel
import com.example.majika.R
import com.example.majika.cart.CartApplication
import com.example.majika.cart.CartViewModel
import com.example.majika.cart.CartViewModelFactory
import com.example.majika.models.Qr
import com.example.majika.utils.RetrofitClient
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FragmentQr(private val totalPrice: String, val fragmentNavbar: FragmentNavbar) : Fragment(){
    private lateinit var viewModel: PageViewModel
    private lateinit var fragmentContext: Context
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var preview: Preview
    private lateinit var viewFinder: PreviewView
    private lateinit var scannerOptions: BarcodeScannerOptions
    private lateinit var scanner: BarcodeScanner
    private lateinit var qr_output: TextView
    private lateinit var status_image: ImageView
    lateinit var mainHandler: Handler
    private val cartViewModel: CartViewModel by activityViewModels {
        CartViewModelFactory(
            (activity?.application as CartApplication)
        )
    }

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

        viewModel = ViewModelProvider(requireActivity())[PageViewModel::class.java]
        mainHandler = Handler(Looper.getMainLooper())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_qr, container, false)


        viewFinder = view.findViewById(R.id.ViewFinder)
        qr_output = view.findViewById(R.id.qr_output)
        status_image = view.findViewById(R.id.status_image)
        qr_output.text=""

        view.findViewById<TextView>(R.id.qr_total).text = totalPrice

        if (viewModel.cameraPermission.value == true) {
            cameraProviderFuture = ProcessCameraProvider.getInstance(fragmentContext)
            cameraProviderFuture.addListener({
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                preview = Preview.Builder()
                    .build()
                preview.setSurfaceProvider(viewFinder.surfaceProvider)

                val cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview
                    )
                } catch (exc: Exception) {
                    Log.e("ERR", "Use case binding failed", exc)
                }
            }, ContextCompat.getMainExecutor(fragmentContext))

            cameraExecutor = Executors.newSingleThreadExecutor()
        }
        return view
    }
    fun scanQrCode() {
        val bitmap: Bitmap = viewFinder.bitmap ?: Bitmap.createBitmap(
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

                    getPayment(rawValue.toString())
                }
            }
            .addOnFailureListener {
                // Task failed with an exception
                // ..."
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

    private fun getPayment(transaction_id: String) {
        val retrofitClient = RetrofitClient.getPaymentStatus()
        retrofitClient.getPaymentStatus(transaction_id).enqueue(object : Callback<Qr> {
            override fun onResponse(call: Call<Qr>, response: Response<Qr>) {
                if (response.isSuccessful) {
                    val message = response.body()?.status
                    qr_output.text = message.toString()
                    if (message == "SUCCESS") {
                        status_image.setImageResource(R.drawable.success)

                        mainHandler.postDelayed({
                            qr_output.text="Pembayaran Berhasil"
                            fragmentNavbar.backToMenu()
                            cartViewModel.deleteAll()
                        }, 5000)
                    } else {
                        qr_output.text="Pembayaran Gagal"
                        status_image.setImageResource(R.drawable.failed)
                    }

                } else {
                    println("Error: ${response.message()} :(")
                }
            }

            override fun onFailure(call: Call<Qr>, t: Throwable) {
                Log.d("Error", t.message.toString())
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }


        })
    }
}

