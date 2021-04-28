package com.swt.augmentmycampus.businessLogic

import com.swt.augmentmycampus.network.Webservice
import retrofit2.Call
import retrofit2.Response
import java.lang.IllegalStateException
import java.net.URL
import javax.inject.Inject
import kotlin.jvm.Throws

class InvalidUrlException() : Exception("Url not valid!")
class CouldNotReachServerException() : Exception("Server could not reached!")

interface DataBusinessLogic {
    @Throws(InvalidUrlException::class)
    fun getTextFromUrl(url: String): String;

    @Throws(InvalidUrlException::class)
    fun performRestCall(url: String): Response<String>;
}

class DataBusinessLogicImpl @Inject constructor (
    private val urlBusinessLogic: UrlBusinessLogic,
    private val webservice: Webservice
) : DataBusinessLogic {

    override fun performRestCall(url: String): Response<String> {
        return webservice.isUrlOnWhitelist(url).execute();
    }

    override fun getTextFromUrl(url: String): String {
        //if (!urlBusinessLogic.isValidUrlFormat(url)) throw InvalidUrlException()

        val urlResponse = performRestCall(url);
        if (!urlResponse.isSuccessful) throw CouldNotReachServerException()

        val textResponse = webservice.getTextResponse(url).execute()
        if (!textResponse.isSuccessful) throw CouldNotReachServerException()

        val body = textResponse.body()
        if (body is String) return body

        return ""
    }
}