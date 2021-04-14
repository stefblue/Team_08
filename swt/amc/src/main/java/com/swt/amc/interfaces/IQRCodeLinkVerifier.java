package com.swt.amc.interfaces;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface IQRCodeLinkVerifier {
    @RequestMapping(path = "/verifyQRCodeLink"  + "/{qrCodeLink}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<Boolean> verifyQRCodeLink(String qrCodeLink);
}
