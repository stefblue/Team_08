package com.swt.augmentmycampus

import com.swt.augmentmycampus.businessLogic.UrlBusinessLogicImpl
import org.junit.Assert
import org.junit.Test

class UrlBusinessLogicUnitTest {
    private val sut = UrlBusinessLogicImpl()

    @Test
    fun `URL has invalid format`() {
        var invalidUrls = listOf<String>(
            "http://../",
            "http://?",
            "http://??",
            "http://??/",
            "http://#",
            "http://##",
            "http://##/",
            "http://foo.bar?q=Spaces should be encoded",
            "//",
            "//a",
            "///a",
            "///",
            "http:///a",
            "foo.com",
            "rdar://1234",
            "h://test",
            "http:// shouldfail.com")
        
        for (url in invalidUrls) Assert.assertFalse(sut.isValidUrlFormat(url));
    }

    @Test
    fun `URL has valid format`() {
        var validUrls = listOf<String>(
            "http://foo.com/blah_blah/",
            "http://foo.com/blah_blah_(wikipedia)",
            "http://foo.com/blah_blah_(wikipedia)_(again)",
            "http://www.example.com/wpstyle/?p=364",
            "https://www.example.com/foo/?bar=baz&inga=42&quux",
            "http://✪df.ws/123",
            "http://userid:password@example.com:8080",
            "http://userid:password@example.com:8080/",
            "http://userid@example.com",
            "http://userid@example.com/",
            "http://userid@example.com:8080",
            "http://userid@example.com:8080/")

        for (url in validUrls) Assert.assertTrue(sut.isValidUrlFormat(url));
    }
}