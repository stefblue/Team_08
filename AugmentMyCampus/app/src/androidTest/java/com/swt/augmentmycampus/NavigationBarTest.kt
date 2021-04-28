package com.swt.augmentmycampus

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationBarTest {

    // @Rule
    // var intentsRule: IntentsTestRule<CameraActivity> = IntentsTestRule(CameraActivity::class.java)

    @get:Rule
    var mainActivity: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testExistence() {
        mainActivity
        // The whole bar itself
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))

        // Each sub-option of the bar
        onView(withId(R.id.navigation_camera)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_data)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_settings)).check(matches(isDisplayed()))
    }

    @Test
    fun testAbilityToSwitchBetweenFragments() {
        mainActivity

        // Check initial camera fragment
        onView(withId(R.layout.fragment_camera)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_camera)).perform(click())
        onView(withId(R.layout.fragment_camera)).check(matches(isDisplayed()))

        // Check camera -> data -> camera
        onView(withId(R.id.navigation_data)).perform(click())
        onView(withId(R.layout.fragment_data)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_data)).perform(click())
        onView(withId(R.layout.fragment_data)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_camera)).perform(click())
        onView(withId(R.layout.fragment_camera)).check(matches(isDisplayed()))


        // Check camera -> settings -> camera
        onView(withId(R.id.navigation_settings)).perform(click())
        onView(withId(R.layout.fragment_settings)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_settings)).perform(click())
        onView(withId(R.layout.fragment_settings)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_camera)).perform(click())
        onView(withId(R.layout.fragment_camera)).check(matches(isDisplayed()))


        // Check data -> settings -> data (-> camera)
        onView(withId(R.id.navigation_data)).perform(click())
        onView(withId(R.layout.fragment_data)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_settings)).perform(click())
        onView(withId(R.layout.fragment_settings)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_data)).perform(click())
        onView(withId(R.layout.fragment_data)).check(matches(isDisplayed()))
        // we'll switch back to camera to be in a defined state
        onView(withId(R.id.navigation_camera)).perform(click())
        onView(withId(R.layout.fragment_camera)).check(matches(isDisplayed()))
    }

    @Test
    fun testMainActivity() {
        mainActivity
        onView(withId(R.id.scanButton)).check(matches(isDisplayed()))
        onView(withId(R.id.scanButton)).check(matches(withText("Scan")))
        onView(withId(R.id.qrCodeText)).check(matches(isDisplayed()))
        onView(withId(R.id.qrCodeText)).check(matches(withText("Press 'Scan' below")))
    }

    @Test
    fun testScannerView() {
        mainActivity
        onView(withId(R.id.scanButton)).perform(click())
        onView(withClassName(Matchers.containsString("ZXingScannerView"))).check(matches(isDisplayed()))
    }

    @Test
    fun testCameraView() {
        mainActivity
        onView(withId(R.id.qrCodeText)).check(matches(isDisplayed()))
        onView(withText("Press 'Scan' below")).check(matches(isDisplayed()))
        onView(withId(R.id.scanButton)).perform(click())
        onView(withClassName(Matchers.containsString("ZXingScannerView")))

        // Wait for user to scan qr-code (5000ms)
        // result can be either user scanned qr.code
        // or the timeout
        // both return to mainactivity
        Thread.sleep(5500);

        onView(withClassName(Matchers.containsString("ZXingScannerView"))).check(doesNotExist())
        onView(withText("Press 'Scan' below")).check(doesNotExist())
    }

    @Test
    fun testBackButtonOnCameraView() {
        mainActivity
        onView(withId(R.id.qrCodeText)).check(matches(isDisplayed()))
        onView(withText("Press 'Scan' below")).check(matches(isDisplayed()))
        onView(withId(R.id.scanButton)).perform(click())
        onView(withClassName(Matchers.containsString("ZXingScannerView")))

        // Wait for button to be available (500ms)
        Thread.sleep(1000);
        onView(withId(R.id.backButton)).perform(click())

        onView(withClassName(Matchers.containsString("ZXingScannerView"))).check(doesNotExist())
        onView(withText("Press 'Scan' below")).check(matches(isDisplayed()))
    }
}