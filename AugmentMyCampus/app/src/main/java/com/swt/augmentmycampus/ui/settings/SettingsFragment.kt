package com.swt.augmentmycampus.ui.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.swt.augmentmycampus.R
import com.swt.augmentmycampus.ui.LocaleManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    @Inject lateinit var localeManager: LocaleManager
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
                ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        val spinner: Spinner = root.findViewById(R.id.fragment_settings_language)
        spinner.setSelection(getCurrentLanguage())
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (position) {
                    0 -> changeLanguage("en")
                    1 -> changeLanguage("ru")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
        return root
    }

    private fun getCurrentLanguage(): Int {
        if (localeManager == null) return 0

        when (localeManager!!.language!!) {
            "en" -> return 0
            "ru" -> return 1
        }
        return 0
    }

    private fun changeLanguage(language: String) {
        if (localeManager == null || localeManager!!.language!! == language)
            return

        localeManager!!.setNewLocale(requireActivity(), language)

        /*requireActivity().finish()
        val i = Intent(requireActivity(), MainActivity::class.java)
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        requireActivity().overridePendingTransition(0, 0)*/

        requireActivity().recreate()
    }
}