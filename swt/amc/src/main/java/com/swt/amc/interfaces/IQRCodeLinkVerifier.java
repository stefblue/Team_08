package com.swt.amc.interfaces;

import com.swt.amc.exceptions.AmcException;

public interface IQRCodeLinkVerifier {

	String getRedirectLink(String qrCodeLink) throws AmcException;
}
