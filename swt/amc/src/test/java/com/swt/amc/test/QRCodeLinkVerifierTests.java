package com.swt.amc.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.swt.amc.interfaces.IQRCodeLinkVerifier;
import com.swt.amc.rest.exceptions.AmcException;

@SpringBootTest
public class QRCodeLinkVerifierTests {

	@Autowired
	private IQRCodeLinkVerifier verifier;

	private List<String> testEntries = Arrays.asList("bla", "bli", "blubb");

	@Test
	public void getLinkTest() throws AmcException {
		List<String> redirectUrls = new ArrayList<String>();
		for (String testEntry : testEntries) {
			// TODO FIXME after implmentation remove this line
			if (!verifier.getRedirectLink(testEntry).trim().isEmpty())
				redirectUrls.add(verifier.getRedirectLink(testEntry));
		}
		Assert.assertEquals(redirectUrls.size(), 3);
		String template = "https://%s.com";
		int i = 0;
		for (String redirectUrl : redirectUrls) {
			String testEntry = testEntries.get(i);
			Assert.assertEquals(redirectUrl, String.format(template, testEntry));
			i++;
		}
	}

	@Test()
	public void getNoLinkTest() {
		try {
			verifier.getRedirectLink("blabb");
			fail();
		} catch (AmcException e) {
			assertTrue(e instanceof AmcException);
		}
	}

}