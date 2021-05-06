package com.swt.augmentmycampus.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.LocaleList
import androidx.preference.PreferenceManager
import androidx.annotation.RequiresApi
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LocaleManagerEntryPoint {
    val localeManager: LocaleManager
}

class LocaleManager(context: Context?) {
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val defaultLanguage: String

    init {
        var language = Locale.getDefault().language
        if (language != LANGUAGE_ENGLISH && language != LANGUAGE_RUSSIAN)
            language = "en"

        defaultLanguage = language
    }

    fun updateLocale(c: Context): Context {
        return updateResources(c, language)
    }

    fun setNewLocale(c: Context, language: String): Context {
        persistLanguage(language)
        return updateResources(c, language)
    }

    val language: String?
        get() = prefs.getString(LANGUAGE_KEY, defaultLanguage)

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String) {
        // use commit() instead of apply(), because sometimes we kill the application process
        // immediately that prevents apply() from finishing
        prefs.edit().putString(LANGUAGE_KEY, language).commit()
    }

    private fun updateResources(context: Context, language: String?): Context {
        var context = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                setLocaleForApi24(config, locale)
                context = context.createConfigurationContext(config)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1 -> {
                config.setLocale(locale)
                context = context.createConfigurationContext(config)
            }
            else -> {
                config.locale = locale
                res.updateConfiguration(config, res.displayMetrics)
            }
        }
        return context
    }

    @RequiresApi(api = VERSION_CODES.N)
    private fun setLocaleForApi24(config: Configuration, target: Locale) {
        val set: MutableSet<Locale> = LinkedHashSet()
        // bring the target locale to the front of the list
        set.add(target)
        val all = LocaleList.getDefault()
        for (i in 0 until all.size()) {
            // append other locales supported by the user
            set.add(all[i])
        }
        val locales = set.toTypedArray()
        config.setLocales(LocaleList(*locales))
    }

    companion object {
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_RUSSIAN = "ru"
        private const val LANGUAGE_KEY = "language_key"
        fun getLocale(res: Resources): Locale {
            val config = res.configuration
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) config.locales[0] else config.locale
        }
    }
}