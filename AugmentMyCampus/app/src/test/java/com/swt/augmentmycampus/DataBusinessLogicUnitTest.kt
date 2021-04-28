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
        var response = businessLogic.performRestCall("dummy:url");
        assertThat(response, instanceOf(String::class.java))
        } catch (e: Exception) {
            fail();
        }
    }


}

class MockCall : Call<Boolean> {
    override fun enqueue(callback: Callback<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun isExecuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun clone(): Call<Boolean> {
        TODO("Not yet implemented")
    }

    override fun isCanceled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun execute(): Response<Boolean> {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

}

class MockWebservice : Webservice {
    override fun isUrlOnWhitelist(url: String): Call<Boolean> {
        return MockCall();
    }

    override fun getTextResponse(url: String): Call<String> {
        TODO("Not yet implemented")
    }
}