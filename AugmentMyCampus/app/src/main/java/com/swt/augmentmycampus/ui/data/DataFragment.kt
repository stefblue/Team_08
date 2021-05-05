package com.swt.augmentmycampus.ui.data

import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.swt.augmentmycampus.R

class DataFragment : Fragment() {

    private lateinit var dataViewModel: DataViewModel

    val args: DataFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_data, container, false)

        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        dataViewModel.dataText.value = args.dataText

        val tvDataText: TextView = root.findViewById(R.id.fragment_data_text)
        dataViewModel.dataText.observe(
            requireActivity(),
            Observer { tvDataText.text = it.toString() })

        return root
    }
}