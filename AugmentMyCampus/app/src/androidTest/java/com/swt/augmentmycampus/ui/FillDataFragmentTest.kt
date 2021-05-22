package com.swt.augmentmycampus.ui

import android.app.Activity
import android.app.Instrumentation
import android.support.test.InstrumentationRegistry
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.swt.augmentmycampus.MainActivity
import com.swt.augmentmycampus.R
import com.swt.augmentmycampus.businessLogic.DataBusinessLogicImpl
import com.swt.augmentmycampus.dependencyInjection.ApplicationModule
import com.swt.augmentmycampus.dependencyInjection.WebserviceConfiguration
import com.swt.augmentmycampus.ui.data.DataFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FillDataFragmentTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var mainActivity: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testTitleField() {
        mainActivity.scenario.onActivity { a ->
            a.findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigation_camera_to_navigation_data, bundleOf("dataText" to getDummyData()))
        }
        Espresso.onView(ViewMatchers.withId(R.id.label_header)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.label_header)).check(ViewAssertions.matches(ViewMatchers.withText("TestTitle")))
    }

    @Test
    fun testECTSField() {
        mainActivity.scenario.onActivity { a ->
            a.findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigation_camera_to_navigation_data, bundleOf("dataText" to getDummyData()))
        }
        Espresso.onView(ViewMatchers.withId(R.id.label_ects_value)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.label_ects_value)).check(ViewAssertions.matches(ViewMatchers.withText("5")))
    }

    @Test
    fun testNumberField() {
        mainActivity.scenario.onActivity { a ->
            a.findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigation_camera_to_navigation_data, bundleOf("dataText" to getDummyData()))
        }
        Espresso.onView(ViewMatchers.withId(R.id.label_number_value)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.label_number_value)).check(ViewAssertions.matches(ViewMatchers.withText("123")))
    }

    @Test
    fun testSemesterField() {
        mainActivity.scenario.onActivity { a ->
            a.findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigation_camera_to_navigation_data, bundleOf("dataText" to getDummyData()))
        }
        Espresso.onView(ViewMatchers.withId(R.id.label_semester_value)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.label_semester_value)).check(ViewAssertions.matches(ViewMatchers.withText("Sommer")))
    }

    private fun getDummyData(): String {
        return "{\"title\":\"TestTitle\",\"number\":\"123\",\"semester\":\"Sommer\",\"ects\":5,\"lecturer\":\"Lecturer A\",\"content\":\"description\",\"link\":\"link\"}";
    }
}