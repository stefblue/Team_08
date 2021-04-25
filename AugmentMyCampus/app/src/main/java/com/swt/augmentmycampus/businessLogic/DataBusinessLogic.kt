package com.swt.augmentmycampus.businessLogic

class InvalidUrlException() : Exception("Url not valid!")
class UrlNotWhitelistedException() : Exception("Url is not on the whitelist!")
class CouldNotReachServerException() : Exception("Server could not reached!")

interface DataBusinessLogic {
    @Throws(InvalidUrlException::class)
    fun getTextFromUrl(url: String): String;
}

class DataBusinessLogicImpl : DataBusinessLogic {
    override fun getTextFromUrl(url: String): String {
        throw NotImplementedError()
    }
}