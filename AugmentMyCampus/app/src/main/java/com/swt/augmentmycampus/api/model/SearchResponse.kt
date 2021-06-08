package com.swt.augmentmycampus.api.model

import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val data: List<SearchResultItem>
)

@JsonClass(generateAdapter = true)
data class SearchResultItem(val lectureName: String, var dataLink: String)
{
    override fun toString(): String {
        return this.lectureName
    }
}