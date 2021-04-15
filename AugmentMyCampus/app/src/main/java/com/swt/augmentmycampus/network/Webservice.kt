package com.swt.augmentmycampus.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.net.URL

interface Webservice {

    @GET("/whitelist/")
    fun isUrlOnWhitelist(url: URL): Call<Boolean>

    @GET("/{filePath}")
    fun getTextResponse(@Path("filePath") filePath: String): Call<String>
}

