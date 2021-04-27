package com.swt.augmentmycampus

import com.swt.augmentmycampus.dependencyInjection.ApplicationModule
import com.swt.augmentmycampus.dependencyInjection.ConfigurationModule
import com.swt.augmentmycampus.network.Webservice
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class WebserviceUnitTest {
    private lateinit var webservice: Webservice

    @Before
    fun setUp() {
        webservice = ApplicationModule.provideWebservice(
            ApplicationModule.provideOkHttpClient(),
            ConfigurationModule.provideWebserviceConfiguration()
        )
    }

    @Test
    fun `has connection to webservice`() {
        //val request = webservice.getTextResponse("test_url").execute()
        //request.isSuccessful()
        Assert.assertTrue(false);
    }
}