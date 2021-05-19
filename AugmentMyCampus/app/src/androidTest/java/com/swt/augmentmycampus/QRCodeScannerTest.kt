package com.swt.augmentmycampus

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
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
        onView(withResourceName(Matchers.equalTo(zxingName))).check(matches(isDisplayed()))
    }

    @Test
    fun testBackButtonOnCameraView() {
        onView(withClassName(Matchers.containsString(zxingName)))

        Espresso.pressBack();
        onView(withClassName(Matchers.containsString(zxingName))).check(doesNotExist())
        onView(withId(R.id.fragment_camera_id)).check(matches(isDisplayed()))
    }
}