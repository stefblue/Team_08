package com.swt.augmentmycampus.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swt.augmentmycampus.businessLogic.DataBusinessLogic
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val dataBusinessLogic: DataBusinessLogic
    ) : ViewModel() {

    fun getTextData(url: String) : String {
        return dataBusinessLogic.getTextFromUrl(url)
    }
}