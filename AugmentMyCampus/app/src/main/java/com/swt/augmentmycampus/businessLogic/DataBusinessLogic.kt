package com.swt.augmentmycampus.businessLogic

import com.swt.augmentmycampus.network.Webservice
import java.net.URL
import javax.inject.Inject
import kotlin.jvm.Throws

class InvalidUrlException() : Exception("Url not valid!")
class UrlNotWhitelistedException() : Exception("Url is not on the whitelist!")
class CouldNotReachServerException() : Exception("Server could not reached!")

interface DataBusinessLogic {
    @Throws(InvalidUrlException::class)
    fun getTextFromUrl(url: String): String;
}

class DataBusinessLogicImpl @Inject constructor (
    private val urlBusinessLogic: UrlBusinessLogic,
    private val webservice: Webservice
) : DataBusinessLogic {

    override fun getTextFromUrl(url: String): String {
        if (!urlBusinessLogic.isValidUrlFormat(url)) throw InvalidUrlException()

        val urlResponse = webservice.isUrlOnWhitelist(url).execute()
        if (!urlResponse.isSuccessful) throw CouldNotReachServerException()
        if (urlResponse.body() !is Boolean || !(urlResponse.body() as Boolean)) throw UrlNotWhitelistedException()

        val textResponse = webservice.getTextResponse(url).execute()
        if (!textResponse.isSuccessful) throw CouldNotReachServerException()

        val body = textResponse.body()
        if (body is String) return body

        return ""
    }
}