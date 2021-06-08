package com.swt.augmentmycampus.businessLogic

import android.util.Log
import com.squareup.moshi.Json
import com.swt.augmentmycampus.api.model.LectureInformation
import com.swt.augmentmycampus.dependencyInjection.ConfigurationModule
import com.swt.augmentmycampus.network.Webservice
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.util.*
import javax.inject.Inject
import kotlin.jvm.Throws


class InvalidUrlException() : Exception("Url not valid!")
class CouldNotReachServerException() : Exception("Server could not reached!")

interface DataBusinessLogic {
    @Throws(InvalidUrlException::class)
    fun getTextFromUrl(url: String): String;

    @Throws(InvalidUrlException::class)
    fun getLectureInformationFromTag(tag: String): LectureInformation;

    fun getResultsForSearchQuery(query: String): List<LectureInformation>;

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

    override fun getLectureInformationFromTag(tag: String): LectureInformation {
        val response = webservice.getLectureInformationForTag(tag).execute()
        if(!response.isSuccessful) throw java.lang.Exception(response.errorBody().toString())
        return response.body()!!
    }

    override fun getResultsForSearchQuery(query: String): List<LectureInformation> {
        val response = webservice.getSearchResult(query).execute()
        if(!response.isSuccessful) throw java.lang.Exception(response.errorBody().toString())
        return response.body()!!
    }
}