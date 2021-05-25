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
class NavigationBarTest {

    // @Rule
    // var intentsRule: IntentsTestRule<CameraActivity> = IntentsTestRule(CameraActivity::class.java)
    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
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
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))

        // Each sub-option of the bar
        onView(withId(R.id.navigation_camera)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_data)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_settings)).check(matches(isDisplayed()))
    }

    @Test
    fun testAbilityToSwitchBetweenFragments() {
        mainActivity
        // Check initial camera fragment
        onView(withId(R.id.navigation_camera)).perform(click())
        onView(withId(R.id.fragment_camera_id)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_camera)).perform(click())
        onView(withId(R.id.fragment_camera_id)).check(matches(isDisplayed()))

        // Check camera -> data -> camera
        onView(withId(R.id.navigation_data)).perform(click())
        onView(withId(R.id.fragment_data_id)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_data)).perform(click())
        onView(withId(R.id.fragment_data_id)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_camera)).perform(click())
        onView(withId(R.id.fragment_camera_id)).check(matches(isDisplayed()))


        // Check camera -> settings -> camera
        onView(withId(R.id.navigation_settings)).perform(click())
        onView(withId(R.id.fragment_settings_id)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_settings)).perform(click())
        onView(withId(R.id.fragment_settings_id)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_camera)).perform(click())
        onView(withId(R.id.fragment_camera_id)).check(matches(isDisplayed()))


        // Check data -> settings -> data (-> camera)
        onView(withId(R.id.navigation_data)).perform(click())
        onView(withId(R.id.fragment_data_id)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_settings)).perform(click())
        onView(withId(R.id.fragment_settings_id)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_data)).perform(click())
        onView(withId(R.id.fragment_data_id)).check(matches(isDisplayed()))
        // we'll switch back to camera to be in a defined state
        onView(withId(R.id.navigation_camera)).perform(click())
        onView(withId(R.id.fragment_camera_id)).check(matches(isDisplayed()))
    }
}