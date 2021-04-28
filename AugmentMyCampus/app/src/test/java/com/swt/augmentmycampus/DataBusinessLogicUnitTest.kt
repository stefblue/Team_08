package com.swt.augmentmycampus

import com.swt.augmentmycampus.businessLogic.DataBusinessLogic
import com.swt.augmentmycampus.businessLogic.DataBusinessLogicImpl
import com.swt.augmentmycampus.businessLogic.UrlBusinessLogicImpl
import com.swt.augmentmycampus.network.Webservice
import junit.framework.Assert.fail
import okhttp3.OkHttp
import okhttp3.Request
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataBusinessLogicUnitTest {
    private val businessLogic = DataBusinessLogicImpl(UrlBusinessLogicImpl(), MockWebservice())

    @Test
    fun `test webservice response type`() {
        try {
        var response = businessLogic.performRestCall("http://test.com");
        assertThat(response.body(), instanceOf(String::class.java))
        } catch (e: Exception) {
            fail();
        }
    }


}

class MockCall : Call<String> {
    override fun enqueue(callback: Callback<String>) {
        TODO("Not yet implemented")
    }

    override fun isExecuted(): Boolean {
        return true;
    }

    override fun clone(): Call<String> {
        TODO("Not yet implemented")
    }

    override fun isCanceled(): Boolean {
        return false;
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun execute(): Response<String> {
        return Response.success("test")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

}

class MockWebservice : Webservice {
    override fun isUrlOnWhitelist(url: String): Call<String> {
        return MockCall();
    }

    override fun getTextResponse(url: String): Call<String> {
        TODO("Not yet implemented")
    }
}