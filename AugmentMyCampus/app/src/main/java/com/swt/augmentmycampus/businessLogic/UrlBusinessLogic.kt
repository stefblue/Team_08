package com.swt.augmentmycampus.businessLogic

interface UrlBusinessLogic {
    fun isValidUrlFormat(url: String): Boolean
}

class UrlBusinessLogicImpl : UrlBusinessLogic {
    override fun isValidUrlFormat(url: String): Boolean {
        throw NotImplementedError()
    }
}
