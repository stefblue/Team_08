package com.swt.amc.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.swt.amc.interfaces.IQRCodeLinkVerifier;

@SpringBootTest
public class QRCodeLinkVerifierTests {

	@Autowired
	private IQRCodeLinkVerifier verifier;

	@Test
	public void isInWhiteList() {
		boolean returnVal = verifier.isInWhiteList("bla");
		returnVal = returnVal && verifier.isInWhiteList("bli");
		returnVal = returnVal && verifier.isInWhiteList("blubb");
		Assert.assertEquals(returnVal, true);
	}

	@Test
	public void isNotinWhitelist() {
		boolean returnval = verifier.isInWhiteList("blabb");
		Assert.assertEquals(returnval, false);
	}

}