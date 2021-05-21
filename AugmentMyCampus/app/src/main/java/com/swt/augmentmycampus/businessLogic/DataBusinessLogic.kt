package com.swt.augmentmycampus.businessLogic

import com.swt.augmentmycampus.network.Webservice
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject


class InvalidUrlException() : Exception("Url not valid!")
class CouldNotReachServerException() : Exception("Server could not reached!")

interface DataBusinessLogic {
    @Throws(InvalidUrlException::class)
    fun getTextFromUrl(url: String): String;

    @Throws(InvalidUrlException::class)
    fun performRestCall(url: String): String;
}

class DataBusinessLogicImpl @Inject constructor (
    private val urlBusinessLogic: UrlBusinessLogic,
    private val webservice: Webservice
) : DataBusinessLogic {

            override fun performRestCall(url: String): String {
                var ws : Webservice;

                val request: Request = Request.Builder()
                        .url(url)
                        .build()

                val client = OkHttpClient()
                try {
                    client.newCall(request).execute().use { response ->
                        if(!response.isSuccessful) {
                            throw CouldNotReachServerException();
                        }
                        return response.body!!.string()
                    }
                } catch(e : Exception) {
                    throw CouldNotReachServerException();
                }
    }

    override fun getTextFromUrl(url: String): String {
        if (!urlBusinessLogic.isValidUrlFormat(url)) throw InvalidUrlException()
        return  performRestCall(url);
    }
}