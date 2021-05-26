package com.swt.augmentmycampus

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.swt.augmentmycampus.api.model.SearchResponse
import com.swt.augmentmycampus.api.model.SearchResultItem
import com.swt.augmentmycampus.businessLogic.*
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
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.*
import org.junit.runner.RunWith
import javax.inject.Inject



@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
@UninstallModules(ConfigurationModule::class)
class DataBusinessLogicTest {

    @Module
    @InstallIn(SingletonComponent::class)
    object FakeConfigurationModule {
        @Provides
        fun provideTestWebservice() = WebserviceConfiguration("http://localhost:8080/")
    }

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
    }

    @After
    fun tearDown() { mockWebServer.shutdown() }

    @Test
    fun webserviceNotFound_Error() {
        createNotFoundResponse()
        Assert.assertThrows(CouldNotReachServerException::class.java) { dataBusinessLogic.getTextFromUrl("http://localhost:1234") }
    }

    @Test
    fun notAnUrl_Error() {
        Assert.assertThrows(InvalidUrlException::class.java) { dataBusinessLogic.getTextFromUrl("not-url") }
        Assert.assertThrows(InvalidUrlException::class.java) { dataBusinessLogic.getTextFromUrl("http://mw1.google.com/mw-earth-vectordb/kml-samples/gp/seattle/gigapxl/$[level]/r$[y]_c$[x].jpg") }
        Assert.assertThrows(InvalidUrlException::class.java) { dataBusinessLogic.getTextFromUrl("https://sdfasd") }
        Assert.assertThrows(InvalidUrlException::class.java) { dataBusinessLogic.getTextFromUrl("dfdsfdsfdfdsfsdfs") }
        Assert.assertThrows(InvalidUrlException::class.java) { dataBusinessLogic.getTextFromUrl("magnet:?xt=urn:btih:123") }
        Assert.assertThrows(InvalidUrlException::class.java) { dataBusinessLogic.getTextFromUrl("https://w") }
    }

    @Test
    fun urlNotWhitelisted_Error() {
        val url = "http://localhost:8080";

        createUrlInvalidResponse()
        Assert.assertThrows(CouldNotReachServerException::class.java) { dataBusinessLogic.getTextFromUrl(url) }
    }

    @Test
    fun textResponse_Success() {
        val url = FakeConfigurationModule.provideTestWebservice().baseUrl + "test/path/"
        createUrlValidResponse()
        createTextResponse("")
        Assert.assertEquals("\"\"", dataBusinessLogic.getTextFromUrl(url));
    }

    @Test
    fun testSearchResult() {
        try {
            val i1 = SearchResultItem("lec1", "link1");
            val i2 = SearchResultItem("lec2", "link2");
            val list : List<SearchResultItem> = listOf(i1,i2);
            createSearchResponse(list);
            val response = dataBusinessLogic.getResultsForSearchQuery("searchstring");
            MatcherAssert.assertThat(response, CoreMatchers.instanceOf(List::class.java))
            Assert.assertEquals(response.size, 2)
        } catch (e: Exception) {
            junit.framework.Assert.fail();
        }
    }

    private fun createSearchResponse(list : List<SearchResultItem>) {
        var searchResponse = SearchResponse(list)

        mockWebServer.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody(moshi.adapter(SearchResponse::class.java).toJson(searchResponse))
        })
    }

    private fun createNotFoundResponse() {
        mockWebServer.enqueue(MockResponse().apply { setResponseCode(404) })
    }

    private fun createUrlInvalidResponse() {
        mockWebServer.enqueue(MockResponse().apply {
            setResponseCode(500)
            setBody(moshi.adapter(String::class.java).toJson(""))
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