package com.swt.augmentmycampus

import android.content.res.Configuration
import android.support.test.InstrumentationRegistry.getContext
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.swt.augmentmycampus.ui.LocaleManager
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SettingsTest {
    @Inject
    lateinit var localeManager: LocaleManager

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var mainActivity: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
        hiltRule.inject()
        mainActivity
        onView(withId(R.id.navigation_settings)).perform(click())
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun textFieldUserNameExists() {
        onView(withId(R.id.fragment_settings_user)).check(matches(isDisplayed()))
    }

    @Test
    fun textFieldPasswordExists() {
        onView(withId(R.id.fragment_settings_password)).check(matches(isDisplayed()))
    }

    @Test
    fun btnSubmitLoginExists() {
        onView(withId(R.id.fragment_settings_submit)).check(matches(isDisplayed()))
    }

    @Test
    fun canTypeName() {
        mainActivity
        val textToType = "Lorem ipsum dolor sit amet"
        onView(withId(R.id.fragment_settings_user)).perform(typeText(textToType))
        closeSoftKeyboard();
        onView(withId(R.id.fragment_settings_submit)).perform(click());
        onView(withText(textToType)).check(matches(isDisplayed()))
    }

    @Test
    fun canTypePassword()
    {
        val textToType = "Lorem ipsum dolor sit amet"
        onView(withId(R.id.fragment_settings_password)).perform(typeText(textToType))
        closeSoftKeyboard();
        onView(withId(R.id.fragment_settings_submit)).perform(click());
        onView(withText(textToType)).check(matches(isDisplayed()))
    }

    @Test
    fun languageSelectorExists() {
        onView(withId(R.id.fragment_settings_language)).check(matches(isDisplayed()))
    }

    @Test
    fun languageLabelExists() {
        var language = "Language"
        when (localeManager.language) {
            "en" -> language = "Language"
            "ru" -> language = "Язык"
        }
        onView(withText(language)).check(matches(isDisplayed()))
    }

    @Test
    fun languageSelectorHasTwoLanguages() {
        val english = "English"
        onView(withId(R.id.fragment_settings_language)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(english))).perform(click())
        onView(withId(R.id.fragment_settings_language)).check(matches(withSpinnerText(english)))

        val russian = "Pусский"
        onView(withId(R.id.fragment_settings_language)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(russian))).perform(click())
        onView(withId(R.id.fragment_settings_language)).check(matches(withSpinnerText(russian)))
    }

    @Test
    fun languageCanSwitchToEnglish() {
        val languageName = "English"
        val language = "Language"

        onView(withId(R.id.fragment_settings_language)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(languageName))).perform(click())
        onView(withId(R.id.fragment_settings_language)).check(matches(withSpinnerText(languageName)))

        onView(withId(R.id.settings_language_label)).check(matches(withText(language)))
    }

    @Test
    fun languageCanSwitchToRussian() {
        val languageName = "Pусский"
        val language = "Язык"

        onView(withId(R.id.fragment_settings_language)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(languageName))).perform(click())
        onView(withId(R.id.fragment_settings_language)).check(matches(withSpinnerText(languageName)))

        onView(withId(R.id.settings_language_label)).check(matches(withText(language)))
    }
}