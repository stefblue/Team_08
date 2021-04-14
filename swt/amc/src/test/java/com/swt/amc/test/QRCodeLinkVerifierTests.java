package com.swt.amc.test;

import com.swt.amc.interfaces.IQRCodeLinkVerifier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import javax.xml.ws.Response;

@SpringBootTest
public class QRCodeLinkVerifierTests {

    @Autowired
    private IQRCodeLinkVerifier verifier;
    @Test
    public void test(){
        ResponseEntity<Boolean> retrunval =  verifier.verifyQRCodeLink("Test");

        Assert.isTrue(retrunval.getBody());
    }
}
