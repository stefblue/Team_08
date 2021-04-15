package com.swt.augmentmycampus.businessLogic

class InvalidUrlException() : Exception("Url not valid")

interface DataBusinessLogic {
    @Throws(InvalidUrlException::class)
    fun getTextFromUrl(url: String): String;
}