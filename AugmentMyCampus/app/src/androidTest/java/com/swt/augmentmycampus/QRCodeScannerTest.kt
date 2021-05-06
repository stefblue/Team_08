package com.swt.augmentmycampus

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
import okhttp3.internal.wait
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class QRCodeScannerTest {

    // @Rule
    // var intentsRule: IntentsTestRule<CameraActivity> = IntentsTestRule(CameraActivity::class.java)
    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)

    @get:Rule
    var mainActivity: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    val zXingScannerView = "ZXingScannerView"

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
        onView(withId(R.id.scanButton)).check(matches(isDisplayed()))
    }

    @Test
    fun testScannerView() {
        onView(withId(R.id.scanButton)).perform(click())
        onView(withClassName(Matchers.containsString(zXingScannerView))).check(matches(isDisplayed()))
    }

    @Test
    fun testCameraView() {
        onView(withId(R.id.navigation_camera)).check(matches(isDisplayed()))

        onView(withId(R.id.scanButton)).perform(click())
        onView(withClassName(Matchers.containsString(zXingScannerView)))

        // Wait for user to scan qr-code (5000ms)
        // result can be either user scanned qr.code
        // or the timeout
        // both return to mainactivity
        Thread.sleep(5500);

        onView(withClassName(Matchers.containsString(zXingScannerView))).check(doesNotExist())
    }

    @Test
    fun testBackButtonOnCameraView() {
        onView(withId(R.id.scanButton)).perform(click())
        onView(withClassName(Matchers.containsString(zXingScannerView)))

        // Wait for button to be available (500ms)
        Thread.sleep(1000);

        onView(withId(R.id.backButton)).perform(click())
        onView(withClassName(Matchers.containsString(zXingScannerView))).check(doesNotExist())
        onView(withId(R.id.navigation_camera)).check(matches(isDisplayed()))
    }
}