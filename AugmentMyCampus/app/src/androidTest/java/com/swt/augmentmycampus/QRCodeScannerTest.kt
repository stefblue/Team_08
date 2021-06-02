package com.swt.augmentmycampus

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class QRCodeScannerTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var mainActivity: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    val zxingName = "zxing_barcode_scanner"

    @Before
    fun setUp() {
        Intents.init()
        mainActivity
        onView(withId(R.id.navigation_camera)).perform(click())
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testMainActivity() {
        onView(withId(R.id.navigation_camera)).check(matches(isDisplayed()))
    }

    @Test
    fun testScannerView() {
        onView(withId(R.id.barcode_scanner)).check(matches(isDisplayed()))
    }

    @Test
    fun testScannerFlashButtonView() {
        onView(withId(R.id.barcode_scanner)).check(matches(isDisplayed()))
        onView(withId(R.id.flash_button)).check(matches(isDisplayed()))
    }

    @Test
    fun testScannerFlashButtonToggleFlash() {
        onView(withId(R.id.barcode_scanner)).check(matches(isDisplayed()))
        onView(withId(R.id.flash_button)).check(matches(isDisplayed()))
        onView(withId(R.id.flash_button)).perform(click())
        onView(withId(R.id.flash_button)).check(matches(isDisplayed()))
        onView(withId(R.id.flash_button)).perform(click())
        onView(withId(R.id.flash_button)).check(matches(isDisplayed()))
    }
}
