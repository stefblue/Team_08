package com.swt.amc.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.configuration.AmcConfiguration;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;

@Component
public class QrCodeLinkVerifier implements IQRCodeLinkVerifier {

	@Autowired
	private AmcConfiguration amcConfig;

	private boolean isInWhiteList(String qrCodeLink) {
		return amcConfig.getUrlWhiteListMap().containsKey(qrCodeLink);
	}

	@Override
	public String getRedirectLink(String qrCodeLink) throws AmcException {

		if (isInWhiteList(qrCodeLink)) {
			return amcConfig.getUrlWhiteListMap().get(qrCodeLink);
		} else {
			throw new AmcException("No mapping found for the given key!");
		}

	}

}
