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
import com.swt.augmentmycampus.businessLogic.DataBusinessLogic
import com.swt.augmentmycampus.businessLogic.DataBusinessLogicImpl
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
import org.hamcrest.Matchers
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
        fun provideTestWebservice() = WebserviceConfiguration("http://localhost:8080/")
    }

    @get:Rule
    var mainActivity: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var mockWebServer: MockWebServer

    private lateinit var dataBusinessLogic: DataBusinessLogic

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var okHttpClient: OkHttpClient

    var moshi = ApplicationModule.provideJsonSerializer()

    @Before
    fun init() {
        hiltRule.inject()
        val idlingResource: IdlingResource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(idlingResource)

        mockWebServer = MockWebServer()
        mockWebServer.start(port = 8080)

        dataBusinessLogic = DataBusinessLogicImpl(
                ApplicationModule.provideUrlBusinessLogic(),
                ApplicationModule.provideWebservice(
                        ApplicationModule.provideOkHttpClient(),
                        moshi,
                        WebserviceConfiguration("http://localhost:8080")
                )
        )
        Intents.init()
        mainActivity
        onView(ViewMatchers.withId(R.id.navigation_camera)).perform(ViewActions.click())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Intents.release()
    }

    @Test
    fun scanQRCodeAndDisplayResult() {
        val textToDisplay = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr."

        mockWebServer.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody(moshi.adapter(String::class.java).toJson(textToDisplay))
        })

        //onView(ViewMatchers.withId(R.id.qrCodeText)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //onView(ViewMatchers.withText("Press 'Scan' below")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.scanButton)).perform(ViewActions.click())
        onView(ViewMatchers.withClassName(Matchers.containsString("ZXingScannerView")))

        Thread.sleep(5500) // wait for user to scan qr code and app to display result

        onView(ViewMatchers.withId(R.id.navigation_data)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText(textToDisplay)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}