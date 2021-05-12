package com.swt.augmentmycampus

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.swt.augmentmycampus.ui.LocaleManagerEntryPoint
import dagger.hilt.android.EntryPointAccessors

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        var localeManager = EntryPointAccessors.fromApplication(newBase, LocaleManagerEntryPoint::class.java).localeManager
        var context = newBase
        if (localeManager != null)
            context = localeManager!!.updateLocale(context)
        super.attachBaseContext(context)
    }
}