package com.swt.augmentmycampus.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import java.net.URL

interface Webservice {

    @GET("/verifyQrCode/{url}")
    fun isUrlOnWhitelist(@Path("url") url: String): Call<String>

    @GET
    fun getTextResponse(@Url url: String): Call<String>
}

