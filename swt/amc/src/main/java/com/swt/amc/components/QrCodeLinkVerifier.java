package com.swt.amc.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.configuration.AmcConfiguration;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;

@Component
public class QrCodeLinkVerifier implements IQRCodeLinkVerifier {

	@Autowired
	private AmcConfiguration amcConfig;

	private boolean isInWhiteList(String qrCodeLink) {
		return amcConfig.getUrlWhiteListMap().containsKey(qrCodeLink);
	}

	@Override
	public String getRedirectLink(String qrCodeLink) {
		// TODO impl and use function from above!
		return "";
	}

}
