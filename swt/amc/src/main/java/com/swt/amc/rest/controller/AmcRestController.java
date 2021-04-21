package com.swt.amc.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.swt.amc.interfaces.IQRCodeLinkVerifier;

@RestController
public class AmcRestController {

	@Autowired
	private IQRCodeLinkVerifier qrCodeLinkVerifier;

	@GetMapping("/verifyQrCode/{qrCodeLink}")
	public boolean greeting(@PathVariable("qrCodeLink") final String qrCodeLink) {
		return qrCodeLinkVerifier.isInWhiteList(qrCodeLink);
	}

}
