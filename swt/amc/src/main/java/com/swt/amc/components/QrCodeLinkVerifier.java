package com.swt.amc.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.configuration.AmcConfiguration;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;

@Component
public class QrCodeLinkVerifier implements IQRCodeLinkVerifier {

    @Autowired
	private AmcConfiguration configuration;

	private boolean isInWhiteList(String qrCodeLink) {
		return configuration.getUrlWhiteListMap().containsKey(qrCodeLink);
	}

	@Override
	public String getRedirectLink(String qrCodeLink) throws AmcException {

		if (isInWhiteList(qrCodeLink)) {
			return configuration.getUrlWhiteListMap().get(qrCodeLink).getLink();
		} else {
			throw new AmcException("No mapping found for the given key!");
		}
	}

	@Override
	public LectureInformation getLectureInformation(String qrCodeLink) throws AmcException {
		if (isInWhiteList(qrCodeLink)) {
			return configuration.getUrlWhiteListMap().get(qrCodeLink);
		} else {
			throw new AmcException("No mapping found for the given key!");
		}
	}
}
