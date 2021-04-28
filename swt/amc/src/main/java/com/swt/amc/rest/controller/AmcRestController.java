package com.swt.amc.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.swt.amc.interfaces.IQRCodeLinkVerifier;
import com.swt.amc.rest.exceptions.AmcException;

@RestController
public class AmcRestController {

	@Autowired
	private IQRCodeLinkVerifier qrCodeLinkVerifier;

	@GetMapping("/verifyQrCode/{qrCodeLink}")
	public String verifyQrCode(@PathVariable("qrCodeLink") final String qrCodeLink) {
		try {
			return qrCodeLinkVerifier.getRedirectLink(qrCodeLink);
		} catch (AmcException e) {
			// TODO impl
			return "";
		}
	}

}
