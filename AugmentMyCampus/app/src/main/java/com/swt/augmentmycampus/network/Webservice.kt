package com.swt.augmentmycampus.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import java.net.URL

interface Webservice {

    @GET("/whitelist/{url}")
    fun isUrlOnWhitelist(@Path("url") url: URL): Call<Boolean>

    @GET
    fun getTextResponse(@Url url: String): Call<String>
}

