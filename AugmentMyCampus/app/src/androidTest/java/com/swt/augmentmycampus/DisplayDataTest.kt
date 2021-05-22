package com.swt.augmentmycampus

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.jakewharton.espresso.OkHttp3IdlingResource
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
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
@UninstallModules(ConfigurationModule::class)
class DisplayDataTest {

    @Module
    @InstallIn(SingletonComponent::class)
    object FakeConfigurationModule {
        @Provides
        fun provideWebserviceConfiguration() = WebserviceConfiguration("http://localhost:8080/")
    }

    private lateinit var mockWebServer: MockWebServer

    @get:Rule (order = 0)
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

        onView(ViewMatchers.withId(R.id.navigation_camera)).perform(ViewActions.click())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Intents.release()
    }

    @Test // Test currently does not mock camera -> needs valid QR code to work
    fun scanQRCodeAndDisplayResult() {
        val textToDisplay = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr."

        createUrlValidResponse()
        createTextResponse(textToDisplay)

        Thread.sleep(15000) // wait for user to scan qr code and app to display result

        onView(ViewMatchers.withId(R.id.navigation_data)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText(textToDisplay)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
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