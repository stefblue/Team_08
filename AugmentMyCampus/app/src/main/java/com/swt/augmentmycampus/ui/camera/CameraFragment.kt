package com.swt.augmentmycampus.ui.camera

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.swt.augmentmycampus.R
import com.swt.augmentmycampus.businessLogic.*
import com.swt.augmentmycampus.network.Webservice
import com.swt.augmentmycampus.ui.data.DataFragment
import com.swt.augmentmycampus.ui.data.DataFragmentArgs
import com.swt.augmentmycampus.ui.data.DataViewModel
import com.swt.augmentmycampus.ui.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private lateinit var cameraViewModel: CameraViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_camera, container, false)
        cameraViewModel = ViewModelProvider(this).get(CameraViewModel::class.java)

        val scanButton: Button = root.findViewById(R.id.scanButton) as Button
        scanButton.setOnClickListener(View.OnClickListener { scanButtonClick() })

        return root
    }

    private fun scanButtonClick() {
        Log.i("MainActivity", "scanButtonClick")

        val intentIntegrator = IntentIntegrator.forSupportFragment(this)
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.setCameraId(0)
        intentIntegrator.setPrompt(getString(R.string.scan_info))
        intentIntegrator.setBarcodeImageEnabled(false)
        intentIntegrator.setOrientationLocked(true);

        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        Log.i("scanned: ", result.contents)

        if (result != null && result.contents != null) {
            Toast.makeText(context, getString(R.string.scanned) + " "
                    + result.contents, Toast.LENGTH_SHORT).show()
            try {
                var resultText = cameraViewModel.getTextData(result.contents); // get data from BL

                val action = CameraFragmentDirections.actionNavigationCameraToNavigationData(resultText)
                requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)

            } catch (ex: InvalidUrlException) {
                Log.e("ex", ex.toString());
                Toast.makeText(context, getString(R.string.error_invalid_url), Toast.LENGTH_LONG).show()
            } catch (ex: UrlNotWhitelistedException) {
                Log.e("ex", ex.toString());
                Toast.makeText(context, getString(R.string.error_invalid_url), Toast.LENGTH_LONG).show()
            } catch (ex: CouldNotReachServerException) {
                Log.e("ex", ex.toString());
                Toast.makeText(context, getString(R.string.error_no_connection), Toast.LENGTH_LONG).show()
            }
            catch (ex: Exception) {
                Log.e("cannot process qrcode: ", ex.toString());
                Toast.makeText(context, ex.message, Toast.LENGTH_LONG).show()
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}