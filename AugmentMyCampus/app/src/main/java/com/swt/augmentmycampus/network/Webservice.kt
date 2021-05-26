package com.swt.augmentmycampus.network

import com.swt.augmentmycampus.api.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import java.net.URL

interface Webservice {

    @GET("/verifyQrCode/{url}")
    fun isUrlOnWhitelist(@Path("url") url: String): Call<String>

    @GET
    fun getTextResponse(@Url url: String): Call<String>

    @GET("/search")
    fun getSearchResult(@Query("query") query : String): Call<SearchResponse>
}

