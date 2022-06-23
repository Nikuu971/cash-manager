package com.epitech.cashmanager.qrcode



import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.epitech.cashmanager.R
import com.epitech.cashmanager.databinding.FragmentQrCodeBinding
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.otaliastudios.cameraview.frame.Frame


class QRCodeFragment : Fragment() {

    internal  var isDetected = false
    lateinit var options: FirebaseVisionBarcodeDetectorOptions
    lateinit var detector:FirebaseVisionBarcodeDetector
    private lateinit var binding : FragmentQrCodeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qr_code, container, false)

        setupCamera()

        return binding.root
    }

    private fun setupCamera() {
        options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
            .build()
        detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)
        binding.btnAgain.isEnabled = isDetected
        binding.btnAgain.setOnClickListener{
            isDetected = !isDetected
            binding.btnAgain.isEnabled = isDetected
        }
        binding.cameraView.setLifecycleOwner(this)
        binding.cameraView.addFrameProcessor{ frame -> processImage(getVisionImageFromFrame(frame))}

    }

    private fun processImage(image: FirebaseVisionImage) {
        if (!isDetected)
            detector.detectInImage(image)
                .addOnFailureListener{ e -> Toast.makeText(context, ""+e.message, Toast.LENGTH_LONG).show()}
                .addOnSuccessListener { firebaseVisionBarcodes ->
                    processResult(firebaseVisionBarcodes)
                }
    }

    private fun processResult(firebaseVisionBarcodes: List<FirebaseVisionBarcode>) {
        if (firebaseVisionBarcodes.isNotEmpty()){
            isDetected = true
            binding.btnAgain.isEnabled = isDetected
            for (item in firebaseVisionBarcodes){
                when(item.valueType){
                    FirebaseVisionBarcode.TYPE_TEXT -> {
                        redirectPayment(item.rawValue)
                    }
                }
            }
        }
    }

    private fun createDialog(text: String?) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(text)
            .setPositiveButton("ok") { dialogInterface, i -> dialogInterface.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    private fun getVisionImageFromFrame(frame: Frame): FirebaseVisionImage {
        val data = frame.getData<ByteArray>()
        val metadata = FirebaseVisionImageMetadata.Builder()
            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
            .setHeight(frame.size.height)
            .setWidth(frame.size.width)
            .build()

        return FirebaseVisionImage.fromByteArray(data, metadata)
    }

    private fun redirectPayment(text: String?){
        if (text == "true" || text == "1"){
            createDialog(text)
            //NavHostFragment.findNavController(this).navigate(R.id.action_QRCodeFragment_to_paymentAcceptedFragment)
        }else if(text == "false" || text == "0"){
            createDialog(text)
            //NavHostFragment.findNavController(this).navigate(R.id.action_QRCodeFragment_to_paymentRefusedFragment)
        }else{
            createDialog("Invalide information")
        }
    }

}