package com.swt.amc.components;

import org.springframework.stereotype.Component;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;

@Component
public class QrCodeLinkVerifierComponent implements IQRCodeLinkVerifier {

	@Override
	public String getRedirectLink(String qrCodeLink) throws AmcException {
		return null;
	}

	@Override
	public LectureInformation getLectureInformation(String qrCodeLink) throws AmcException {
		return null;
	}

}
