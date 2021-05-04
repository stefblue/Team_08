package com.swt.augmentmycampus

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsTest {

    @get:Rule
    var mainActivity: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
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
}