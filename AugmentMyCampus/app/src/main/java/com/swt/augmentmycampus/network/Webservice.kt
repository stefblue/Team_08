package com.swt.augmentmycampus.network

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import retrofit2.Call
import retrofit2.http.*
import java.net.URL

@JsonClass(generateAdapter = true)
data class UserInformationResponse (
    @SerializedName("givenName")
    val givenName: String,
    @SerializedName("lastName")
    val lastName: String
        )

@JsonClass(generateAdapter = true)
data class LoginRequest (
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)

interface Webservice {

    @GET("/verifyQrCode/{tag}")
    fun isUrlOnWhitelist(@Path("tag") tag: String): Call<String>

    @GET
    fun getTextResponse(@Url url: String): Call<String>

    @POST("/login")
    @Headers("Content-Type: application/json")
    fun login(@Body userInfo: LoginRequest): Call<UserInformationResponse>
}

