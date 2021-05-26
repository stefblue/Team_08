package com.swt.augmentmycampus.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.swt.augmentmycampus.R

class UserInformationFragment(val givenName: String, val lastName: String) : Fragment() {

    companion object {
        fun newInstance() = UserInformationFragment("","")
    }

    private lateinit var viewModel: UserInformationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_information, container, false)
        root.findViewById<TextView>(R.id.user_information_givenName).apply {
            text = givenName
        }
        root.findViewById<TextView>(R.id.user_information_lastName).apply {
            text = lastName
        }
        val button = root.findViewById(R.id.fragment_settings_logout) as Button
        button.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_settings_user_container, LoginFragment())
                .commit()
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserInformationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}