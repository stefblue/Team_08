package com.swt.amc.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.swt.amc.interfaces.IQRCodeLinkVerifier;

@Component
public class QrCodeLinkVerifier implements IQRCodeLinkVerifier {

	@Override
	public ResponseEntity<Boolean> verifyQRCodeLink(String qrCodeLink) {

		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}

}
