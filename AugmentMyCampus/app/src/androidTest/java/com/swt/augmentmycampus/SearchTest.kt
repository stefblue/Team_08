package com.swt.augmentmycampus

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.swt.augmentmycampus.api.model.SearchResponse
import com.swt.augmentmycampus.api.model.SearchResultItem
import com.swt.augmentmycampus.dependencyInjection.ApplicationModule
import com.swt.augmentmycampus.dependencyInjection.ConfigurationModule
import com.swt.augmentmycampus.dependencyInjection.WebserviceConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matcher
import org.hamcrest.Matchers.equalTo
import org.junit.*
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
@UninstallModules(ConfigurationModule::class)
class SearchTest {

    @Module
    @InstallIn(SingletonComponent::class)
    object FakeConfigurationModule {
        @Provides
        fun provideWebserviceConfiguration() = WebserviceConfiguration("http://localhost:8080/")
    }

    private lateinit var mockWebServer: MockWebServer

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var mainActivity: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var okHttpClient: OkHttpClient

    var moshi = ApplicationModule.provideJsonSerializer()

    @Before
    fun init() {
        hiltRule.inject()
        Intents.init()
        val idlingResource: IdlingResource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(idlingResource)

        mockWebServer = MockWebServer()
        mockWebServer.start(port = 8080)

        onView(ViewMatchers.withId(R.id.navigation_search)).perform(ViewActions.click())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Intents.release()
    }

    @Test
    fun displaySearchResults() {
        val i1 = SearchResultItem("lec1", "http://localhost:8080/test1");
        val i2 = SearchResultItem("lec2", "http://localhost:8080/test2");
        val list : List<SearchResultItem> = listOf(i1,i2);
        createSearchResponse(list);

        onView(withId(R.id.search_field)).perform(click())
            .perform(typeText("some query"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        onView(withId(android.R.id.list)).check(matches(hasChildCount(2)))
        onView(withText(i1.lectureName)).check(matches(isDisplayed()))
        onView(withText(i2.lectureName)).check(matches(isDisplayed()))
    }

    @Test
    fun displaySearchResultsDetails() {
        val textToDisplay = "test"

        val i1 = SearchResultItem("lec1", "http://localhost:8080/test1");
        val i2 = SearchResultItem("lec2", "http://localhost:8080/test2");
        val list : List<SearchResultItem> = listOf(i1,i2);
        createSearchResponse(list);

        onView(withId(R.id.search_field)).perform(click())
                .perform(typeText("some query"))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        createUrlValidResponse()
        createTextResponse(textToDisplay)

        onView(withText(i1.lectureName)).perform(click())
        onView(withId(R.id.navigation_data)).check(matches(isDisplayed()))
        onView(withText(textToDisplay)).check(matches(isDisplayed()))
    }

    private fun createSearchResponse(list : List<SearchResultItem>) {
        val searchResponse = SearchResponse(list)
        mockWebServer.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody(moshi.adapter(SearchResponse::class.java).toJson(searchResponse))
        })
    }

    private fun createUrlValidResponse() {
        mockWebServer.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody(moshi.adapter(String::class.java).toJson(""))
        })
    }

    private fun createTextResponse(text: String) {
        mockWebServer.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody(moshi.adapter(String::class.java).toJson(text))
        })
    }
}