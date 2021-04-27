package com.swt.amc.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.configuration.AmcConfiguration;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;

@Component
public class QrCodeLinkVerifier implements IQRCodeLinkVerifier {

	@Autowired
	private AmcConfiguration amcConfig;

	@Override
	public boolean isInWhiteList(String qrCodeLink) {

		return amcConfig.getUrlWhiteList().contains(qrCodeLink);
	}

}
