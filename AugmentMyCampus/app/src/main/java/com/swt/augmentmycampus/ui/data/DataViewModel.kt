package com.swt.augmentmycampus.ui.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class DataViewModel : ViewModel() {
    val dataText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}