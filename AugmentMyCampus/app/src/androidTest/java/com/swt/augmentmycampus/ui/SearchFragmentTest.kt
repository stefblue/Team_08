package com.swt.augmentmycampus.ui

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

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var mainActivity: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testNavigationToSearchFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.fragment_search))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testSearchFieldExists() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.fragment_search))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.search_field))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}