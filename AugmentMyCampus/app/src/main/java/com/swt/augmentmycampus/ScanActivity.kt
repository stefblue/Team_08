package com.swt.augmentmycampus

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import dagger.hilt.android.AndroidEntryPoint
import me.dm7.barcodescanner.zxing.ZXingScannerView

@AndroidEntryPoint
class ScanActivity : BaseActivity(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity);
    }

    public fun scanButtonClick(view: View) {
        Log.i("MainActivity", "scanButtonClick")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            var list = Array(1) {Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, list, 0);
        }
        mScannerView = ZXingScannerView(this) // Programmatically initialize the scanner view
        setContentView(mScannerView) // Set the scanner view as the content view
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera() // Start camera on resume

        val delayedBackButtonAddHandler = Handler()
        delayedBackButtonAddHandler.postDelayed({ handleBackButton() }, 500)

        val delayedCloseCameraViewHandler = Handler()
        delayedCloseCameraViewHandler.postDelayed({
            Log.i("MainActivity", "camera timeout")
            handleResult(null)
            var textView: TextView = findViewById(R.id.qrCodeText)
            textView.text = "No image found."
        }, 5000)
    }

    private fun handleBackButton() {
        // delay to wait for camera preview View
        // last added child to FrameLayout is on top
        // we need to make sure that the back-button is on top
        val factory = LayoutInflater.from(this)
        val myView: View = factory.inflate(R.layout.backbutton, null)
        mScannerView!!.addView(myView)
    }

    public fun backButtonClick(view: View) {
        handleResult(null)
    }

    override fun handleResult(rawResult: Result?) {
        mScannerView!!.stopCamera()
        setContentView(R.layout.main_activity);
        if (rawResult != null) {
            Log.i("MainActivity", rawResult.text)
            var textView: TextView = findViewById(R.id.qrCodeText);
            textView.text = rawResult.text;
        }
    }
}