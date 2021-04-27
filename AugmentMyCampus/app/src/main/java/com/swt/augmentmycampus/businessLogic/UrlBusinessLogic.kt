package com.swt.augmentmycampus.businessLogic

import android.util.Patterns
import android.webkit.URLUtil
import androidx.core.util.PatternsCompat
import java.net.MalformedURLException
import java.net.URL

interface UrlBusinessLogic {
    fun isValidUrlFormat(url: String): Boolean
}

class UrlBusinessLogicImpl : UrlBusinessLogic {
    override fun isValidUrlFormat(url: String): Boolean {
        try {
            var myUrl = URL(url)
            return myUrl.host == "localhost" || PatternsCompat.WEB_URL.matcher(url).matches()
        } catch (ex: Exception) {
            // Do nothing
        }
        return false
    }
}
