package com.swt.amc.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.swt.amc.interfaces.IQRCodeLinkVerifier;

@SpringBootTest
public class QRCodeLinkVerifierTests {

	@Autowired
	private IQRCodeLinkVerifier verifier;

	@Test
	public void test() {
		ResponseEntity<Boolean> retrunval = verifier.verifyQRCodeLink("Test");

		Assert.assertEquals(retrunval.getBody(), true);
	}

	@Test
	public void isInWhiteList() {
		boolean returnVal = verifier.verifyQRCodeLink("bla").getBody();
		returnVal = returnVal && verifier.verifyQRCodeLink("bli").getBody();
		returnVal = returnVal && verifier.verifyQRCodeLink("blubb").getBody();
		Assert.assertEquals(returnVal, true);
	}

	@Test
	public void isNotinWhitelist() {
		ResponseEntity<Boolean> returnval = verifier.verifyQRCodeLink("blabb");
		Assert.assertEquals(returnval.getBody(), !returnval.getBody());
	}

}
