package com.swt.augmentmycampus

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.swt.augmentmycampus.dependencyInjection.ApplicationModule
import com.swt.augmentmycampus.dependencyInjection.ConfigurationModule
import com.swt.augmentmycampus.dependencyInjection.WebserviceConfiguration
import com.swt.augmentmycampus.network.UserInformationResponse
import com.swt.augmentmycampus.ui.LocaleManager
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
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(ConfigurationModule::class)
class SettingsTest {

    @Module
    @InstallIn(SingletonComponent::class)
    object FakeConfigurationModule {
        @Provides
        fun provideTestWebservice() = WebserviceConfiguration("http://localhost:8080/")
    }

    private lateinit var mockWebServer: MockWebServer

    var moshi = ApplicationModule.provideJsonSerializer()

    @Inject
    lateinit var localeManager: LocaleManager

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var mainActivity: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp() {
        Intents.init()
        hiltRule.inject()
        mainActivity
        val idlingResource: IdlingResource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(idlingResource)

        mockWebServer = MockWebServer()
        mockWebServer.start(port = 8080)

        onView(withId(R.id.navigation_settings)).perform(click())
    }

    @After
    fun tearDown() {
        Intents.release()
        mockWebServer.shutdown()
    }

    @Test
    fun textFieldUserNameExists() {
        onView(withId(R.id.fragment_login_user)).check(matches(isDisplayed()))
    }

    @Test
    fun textFieldPasswordExists() {
        onView(withId(R.id.fragment_login_password)).check(matches(isDisplayed()))
    }

    @Test
    fun btnSubmitLoginExists() {
        onView(withId(R.id.fragment_login_submit)).check(matches(isDisplayed()))
    }

    @Test
    fun canTypeName() {
        mainActivity
        val textToType = "Lorem ipsum dolor sit amet"
        onView(withId(R.id.fragment_login_user)).perform(typeText(textToType))
        closeSoftKeyboard()
        onView(withText(textToType)).check(matches(isDisplayed()))
    }

    @Test
    fun canTypePassword()
    {
        val textToType = "Lorem ipsum dolor sit amet"
        onView(withId(R.id.fragment_login_password)).perform(typeText(textToType))
        closeSoftKeyboard()
        onView(withText(textToType)).check(matches(isDisplayed()))
    }

    @Test
    fun loginFailsWitWrongCredentials() {

        mockWebServer.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody(moshi.adapter(UserInformationResponse::class.java).toJson(null))
        })

        val textToType = "notauser"
        onView(withId(R.id.fragment_login_user)).perform(typeText(textToType))
        closeSoftKeyboard()
        onView(withId(R.id.fragment_login_password)).perform(typeText(textToType))
        closeSoftKeyboard()
        onView(withId(R.id.fragment_login_submit)).perform(click())

        var invalid = "Invalid user or password"
        when (localeManager.language) {
            "en" -> invalid = "Invalid user or password"
            "ru" -> invalid = "Неверный пользователь или пароль"
        }
        onView(withText(invalid)).check(matches(isDisplayed()))
    }

    @Test
    fun loginWithRightCredentials1() {
        mockWebServer.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody(moshi.adapter(UserInformationResponse::class.java).toJson(UserInformationResponse("John", "Smith")))
        })

        val username = "john"
        val password = "123456"
        onView(withId(R.id.fragment_login_user)).perform(typeText(username))
        closeSoftKeyboard()
        onView(withId(R.id.fragment_login_password)).perform(typeText(password))
        closeSoftKeyboard()
        onView(withId(R.id.fragment_login_submit)).perform(click())

        onView(withText("John")).check(matches(isDisplayed()))
        onView(withText("Smith")).check(matches(isDisplayed()))
    }

    @Test
    fun loginWithRightCredentials2() {
        mockWebServer.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody(moshi.adapter(UserInformationResponse::class.java).toJson(UserInformationResponse("Karl", "Heinz")))
        })

        val username = "karl"
        val password = "123456"
        onView(withId(R.id.fragment_login_user)).perform(typeText(username))
        closeSoftKeyboard()
        onView(withId(R.id.fragment_login_password)).perform(typeText(password))
        closeSoftKeyboard()
        onView(withId(R.id.fragment_login_submit)).perform(click())

        onView(withText("Karl")).check(matches(isDisplayed()))
        onView(withText("Heinz")).check(matches(isDisplayed()))
    }

    @Test
    fun logoutIsWorking() {
        loginWithRightCredentials1()
        val logoutButton = onView(withText("Logout"))

        onView(withId(R.id.fragment_settings_logout)).perform(click())

        logoutButton.check(doesNotExist())
    }

    @Test
    fun languageSelectorExists() {
        onView(withId(R.id.fragment_settings_language)).check(matches(isDisplayed()))
    }

    @Test
    fun languageLabelExists() {
        var language = "Language"
        when (localeManager.language) {
            "en" -> language = "Language"
            "ru" -> language = "Язык"
        }
        onView(withText(language)).check(matches(isDisplayed()))
    }

    @Test
    fun languageSelectorHasTwoLanguages() {
        val english = "English"
        onView(withId(R.id.fragment_settings_language)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(english))).perform(click())
        onView(withId(R.id.fragment_settings_language)).check(matches(withSpinnerText(english)))

        val russian = "Pусский"
        onView(withId(R.id.fragment_settings_language)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(russian))).perform(click())
        onView(withId(R.id.fragment_settings_language)).check(matches(withSpinnerText(russian)))
    }

    @Test
    fun languageCanSwitchToEnglish() {
        val languageName = "English"
        val language = "Language"

        onView(withId(R.id.fragment_settings_language)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(languageName))).perform(click())
        onView(withId(R.id.fragment_settings_language)).check(matches(withSpinnerText(languageName)))

        onView(withId(R.id.settings_language_label)).check(matches(withText(language)))
    }

    @Test
    fun languageCanSwitchToRussian() {
        val languageName = "Pусский"
        val language = "Язык"

        onView(withId(R.id.fragment_settings_language)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(languageName))).perform(click())
        onView(withId(R.id.fragment_settings_language)).check(matches(withSpinnerText(languageName)))

        onView(withId(R.id.settings_language_label)).check(matches(withText(language)))
    }
}