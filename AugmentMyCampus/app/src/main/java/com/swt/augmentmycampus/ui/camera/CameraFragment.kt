package com.swt.augmentmycampus.ui.camera

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.sax.RootElement
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.Result
import com.swt.augmentmycampus.R
import com.swt.augmentmycampus.businessLogic.CouldNotReachServerException
import com.swt.augmentmycampus.businessLogic.DataBusinessLogic
import com.swt.augmentmycampus.businessLogic.InvalidUrlException
import com.swt.augmentmycampus.businessLogic.UrlNotWhitelistedException
import com.swt.augmentmycampus.ui.data.DataFragment
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.lang.Exception
import javax.inject.Inject


class CameraFragment : Fragment(), ZXingScannerView.ResultHandler {

    /*
    @Inject
    lateinit var dataBusinessLogic: DataBusinessLogic
    */
    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var mainActivity: FragmentActivity;

    private var mScannerView: ZXingScannerView? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cameraViewModel =
                ViewModelProvider(this).get(CameraViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_camera, container, false)
        mainActivity = requireActivity()

        val scanButton: Button = root.findViewById(R.id.scanButton) as Button
        scanButton.setOnClickListener(View.OnClickListener { scanButtonClick() })

        //val backButton: Button = root.findViewById(R.id.backButton) as Button
        //backButton.setOnClickListener(View.OnClickListener { backButtonClick() })

        return root
    }

    private fun scanButtonClick() {
        Log.i("MainActivity", "scanButtonClick")
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            var list = Array(1) { Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(mainActivity, list, 0)
        }
        mScannerView = ZXingScannerView(mainActivity) // Programmatically initialize the scanner view
        mainActivity.setContentView(mScannerView) // Set the scanner view as the content view
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera() // Start camera on resume

        val delayedBackButtonAddHandler = Handler()
        delayedBackButtonAddHandler.postDelayed({ handleBackButton() }, 500)

        val delayedCloseCameraViewHandler = Handler()
        delayedCloseCameraViewHandler.postDelayed({
            Log.i("MainActivity", "camera timeout")

            val builder = AlertDialog.Builder(mainActivity)
            with(builder) {
                setTitle(R.string.error)
                setMessage(R.string.qr_code_invalid)
                setNeutralButton(R.string.acknowledge, null)
                show()
            }
        }, 5000)
    }

    private fun handleBackButton() {
        // delay to wait for camera preview View
        // last added child to FrameLayout is on top
        // we need to make sure that the back-button is on top
        val factory = LayoutInflater.from(mainActivity)
        val myView: View = factory.inflate(R.layout.backbutton, null)
        mScannerView!!.addView(myView)
    }

    override fun handleResult(rawResult: Result?) {
        mScannerView!!.stopCamera()
        if (rawResult != null) {

            val fragment: Fragment = DataFragment()
            val fm: FragmentManager = mainActivity.getSupportFragmentManager()
            val transaction: FragmentTransaction = fm.beginTransaction()
            transaction.replace(R.id.fragment_data_id, fragment)
            transaction.commit()

            Log.i("Scanned QR code data", rawResult.text)

            try {
                var resultText = ""//dataBusinessLogic.getTextFromUrl(rawResult.text);
                var displayDataTextView: TextView = mainActivity.findViewById(R.id.fragment_data_text);
                displayDataTextView.text = resultText;
            } catch (ex: Exception) {
                Log.i("CameraFragment", ex.toString())
                val builder = AlertDialog.Builder(mainActivity)
                with(builder) {
                    setTitle(R.string.error)
                    setMessage(ex.message)
                    setNeutralButton(R.string.acknowledge, null)
                    show()
                }
            }
        }
    }
}