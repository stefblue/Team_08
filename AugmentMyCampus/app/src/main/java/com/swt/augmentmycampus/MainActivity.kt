package com.swt.augmentmycampus

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class MainActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test);
    }

    public fun buttonClick(view: View) {
        Log.i("MainActivity", "buttonClick")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            var list = Array(1) {Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, list, 0);
        }
        mScannerView = ZXingScannerView(this) // Programmatically initialize the scanner view
        setContentView(mScannerView) // Set the scanner view as the content view
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera() // Start camera on resume

        val handler = Handler()
        handler.postDelayed({ handleResult(null) }, 5000)
    }

    override fun onResume() {
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()
    }

    override fun handleResult(rawResult: Result?) {
        if (rawResult != null) {
            Log.i("MainActivity", rawResult.text)
        } else {
            Log.i("MainActivity", "no image found")
        }
        mScannerView!!.stopCamera()
        setContentView(R.layout.test);
        if (rawResult != null) {
            var textview: TextView = findViewById(R.id.qrCodeText);
            textview.text = rawResult.text;
        } else {
            var textview: TextView = findViewById(R.id.qrCodeText);
            textview.text = "no image found";
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}