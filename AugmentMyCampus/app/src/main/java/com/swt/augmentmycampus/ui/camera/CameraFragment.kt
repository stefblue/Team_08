package com.swt.augmentmycampus.ui.camera

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.zxing.ResultPoint
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import com.swt.augmentmycampus.R
import com.swt.augmentmycampus.businessLogic.CouldNotReachServerException
import com.swt.augmentmycampus.businessLogic.InvalidUrlException
import com.swt.augmentmycampus.ui.data.DataFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private lateinit var barcodeScanner:Intent
    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var barcodeView: CompoundBarcodeView
    private var lastText: String? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_camera, container, false)
        cameraViewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
        barcodeView = root.findViewById(R.id.barcode_scanner);
        barcodeScanner = IntentIntegrator.forSupportFragment(this).setCameraId(0).setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES).setBarcodeImageEnabled(false).setOrientationLocked(true).setPrompt(getString(R.string.scan_info)).setCaptureActivity(this.javaClass).createScanIntent()

        barcodeView.initializeFromIntent(barcodeScanner)
        barcodeView.decodeContinuous(callback);
        barcodeView.resume()
        return root
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text == null || result.text == lastText) {
                // Prevent duplicate scans
                return
            }
            lastText = result.text
            barcodeView.setStatusText(result.text)
            handleResult(result.text)
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    private fun handleResult(result: String) {
        Log.i("MainActivity", "scanButtonClick")
        if (result != null) {
            Log.i("scanned: ", result)
            Toast.makeText(context, getString(R.string.scanned) + " "
                    + result, Toast.LENGTH_SHORT).show()
            try {
                var resultText = cameraViewModel.getTextData(result); // get data from BL
                //pass data to DataFragment and switch
                val action = CameraFragmentDirections.actionNavigationCameraToNavigationData(resultText)
                requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)

            } catch (ex: InvalidUrlException) {
                Log.e("ex", ex.toString());
                Toast.makeText(context, getString(R.string.error_invalid_url), Toast.LENGTH_LONG).show()
            } catch (ex: CouldNotReachServerException) {
                Log.e("ex", ex.toString());
                Toast.makeText(context, getString(R.string.error_no_connection), Toast.LENGTH_LONG).show()
            }
            catch (ex: Exception) {
                Log.e("cannot process qrcode: ", ex.toString());
                Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

}