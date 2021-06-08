package com.swt.augmentmycampus.network

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import com.swt.augmentmycampus.api.model.LectureInformation
import com.swt.augmentmycampus.api.model.SearchResponse
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

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

    @GET("/verifyQrCode/{tag}")
    fun getLectureInformationForTag(@Path("tag") tag: String): Call<LectureInformation>

    @GET
    fun getTextResponse(@Url url: String): Call<String>

    @GET("/search/{searchString}")
    fun getSearchResult(@Path("searchString") query : String): Call<List<LectureInformation>>

    @POST("/login")
    @Headers("Content-Type: application/json")
    fun login(@Body userInfo: LoginRequest): Call<UserInformationResponse>
}

