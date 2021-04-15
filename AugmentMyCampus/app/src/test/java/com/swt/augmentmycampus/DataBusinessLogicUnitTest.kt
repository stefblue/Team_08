package com.swt.augmentmycampus

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.swt.augmentmycampus.businessLogic.DataBusinessLogic
import com.swt.augmentmycampus.businessLogic.InvalidUrlException
import com.swt.augmentmycampus.dependencyInjection.WebserviceConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import javax.inject.Inject


@ActivityScoped
class DataBusinessLogic @Inject constructor(
    private val dataBusinessLogic: DataBusinessLogic,
    private val okHttpClient: OkHttpClient
)
{
    
}

class DataBusinessLogicUnitTest {

    @Module
    @InstallIn(SingletonComponent::class)
    object FakeConfigurationModule {
        @Provides
        fun provideTestApiConfiguration() = WebserviceConfiguration("http://localhost:8080/")
    }

    private lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var okHttpClient: OkHttpClient
    @Inject
    lateinit var dataBusinessLogic: DataBusinessLogic

    @Before
    fun init() {
        //okHttpClient = OkHttpClient();
        //dataBusinessLogic = DataBusinessLogic();
        val idlingResource: IdlingResource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(idlingResource)

        mockWebServer = MockWebServer()
        mockWebServer.start(port = 8080)
    }

    @After
    fun tearDown() { mockWebServer.shutdown() }

    @Test
    fun `should fail - not an url`() {

        Assert.assertThrows(InvalidUrlException::class.java) { dataBusinessLogic.getTextFromUrl("test") }

    }

    @Test
    fun `should fail - url not whitelisted`() {

    }

    @Test
    fun `is on whitelist - text response` () {

    }

}