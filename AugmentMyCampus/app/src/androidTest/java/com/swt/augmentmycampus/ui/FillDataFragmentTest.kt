package com.swt.augmentmycampus.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.swt.augmentmycampus.MainActivity
import com.swt.augmentmycampus.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

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

    @Test
    fun testLecturerField() {
        mainActivity.scenario.onActivity { a ->
            a.findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigation_camera_to_navigation_data, bundleOf("dataText" to getDummyData()))
        }
        Espresso.onView(ViewMatchers.withId(R.id.label_lecturer_value)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.label_lecturer_value)).check(ViewAssertions.matches(ViewMatchers.withText("Lecturer A")))
    }

    @Test
    fun testContentField() {
        mainActivity.scenario.onActivity { a ->
            a.findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigation_camera_to_navigation_data, bundleOf("dataText" to getDummyData()))
        }
        Espresso.onView(ViewMatchers.withId(R.id.listTitleContent)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.expandedListItemContent)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.expandedListItemContent)).check(ViewAssertions.matches(ViewMatchers.withText("description")))
    }

    @Test
    fun testDateField() {
        mainActivity.scenario.onActivity { a ->
            a.findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigation_camera_to_navigation_data, bundleOf("dataText" to getDummyData()))
        }
        Espresso.onView(ViewMatchers.withId(R.id.listTitleDates)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.listTitleDates)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // We do have the following mocked values for dates/times:
        // 24.05.2021 10:00
        // 25.05.2021 10:00
        // 25.05.2021 14:00
        // 25.05.2021 18:00
        // 26.05.2021 10:00

        // We only test for the first 3 entries because we can't scroll to the bottom without
        // disabling animations on all of our programmers devices. We no only perform a swipeUp
        // which gives us the 3rd date/time as well.
        Espresso.onView(ViewMatchers.withText("24.05.2021 10:00 for 2:00:00")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("25.05.2021 10:00 for 2:00:00")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Espresso.onView(ViewMatchers.withId(R.id.listTitleDates)).perform(ViewActions.swipeUp())
        //Espresso.onView(ViewMatchers.withText("25.05.2021 14:00 for 2:00:00")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Espresso.onView(ViewMatchers.withText("25.05.2021 18:00 for 2:00:00")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Espresso.onView(ViewMatchers.withText("26.05.2021 10:00 for 2:00:00")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testDateFieldColor() {
        mainActivity.scenario.onActivity { a ->
            a.findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigation_camera_to_navigation_data, bundleOf("dataText" to getDummyData()))
        }
        Espresso.onView(ViewMatchers.withId(R.id.listTitleDates)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.listTitleDates)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("24.05.2021 10:00 for 2:00:00")).check { v, _ -> assert(((v as TextView).background as ColorDrawable).color == Color.GREEN) }
        Espresso.onView(ViewMatchers.withText("25.05.2021 10:00 for 2:00:00")).check { v, _ -> assert(((v as TextView).background as ColorDrawable).color == Color.RED) }
        //Espresso.onView(ViewMatchers.withId(R.id.listTitleDates)).perform(ViewActions.swipeUp())
        //Espresso.onView(ViewMatchers.withText("25.05.2021 14:00 for 2:00:00")).check { v, _ -> assert(((v as TextView).background as ColorDrawable).color == Color.RED) }
    }


    private fun getDummyData(): String {
        return "{\"title\":\"TestTitle\",\"number\":\"123\",\"semester\":\"Sommer\",\"ects\":5,\"lecturer\":\"Lecturer A\",\"content\":\"description\",\"link\":\"link\",\"dates\":[{\"key\":\"2021-05-24T10:00:00.000000\",\"value\":\"PT2H\"},{\"key\":\"2021-05-25T10:00:00.000000\",\"value\":\"PT2H\"},{\"key\":\"2021-05-25T14:00:00.000000\",\"value\":\"PT2H\"},{\"key\":\"2021-05-25T18:00:00.000000\",\"value\":\"PT2H\"},{\"key\":\"2021-05-26T10:00:00.000000\",\"value\":\"PT2H\"}]}";
    }
}