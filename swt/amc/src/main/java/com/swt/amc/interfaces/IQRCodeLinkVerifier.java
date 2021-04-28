package com.swt.amc.interfaces;

import com.swt.amc.rest.exceptions.AmcException;

public interface IQRCodeLinkVerifier {

	String getRedirectLink(String qrCodeLink) throws AmcException;
}
