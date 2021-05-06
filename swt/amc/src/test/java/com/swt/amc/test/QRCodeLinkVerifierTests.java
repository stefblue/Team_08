package com.swt.amc.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;

@SpringBootTest
public class QRCodeLinkVerifierTests {

	@Autowired
	private IQRCodeLinkVerifier verifier;

	private List<String> testEntries = Arrays.asList("bla", "bli", "blubb");

	@Test
	public void getLinkTest() throws AmcException {
		List<String> redirectUrls = new ArrayList<String>();
		for (String testEntry : testEntries) {
			redirectUrls.add(verifier.getRedirectLink(testEntry));
		}
		Assert.assertEquals(redirectUrls.size(), 3);
		String template = "https://%s.com";
		int i = 0;
		for (String redirectUrl : redirectUrls) {
			String testEntry = testEntries.get(i);
			assertEquals(redirectUrl, String.format(template, testEntry));
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

	@Test
	public void getLectureInformationTest() throws AmcException {
		List<LectureInformation> lectureInformations = new ArrayList<LectureInformation>();
		for (String testEntry : testEntries) {
			lectureInformations.add(verifier.getLectureInformation(testEntry));
		}
		int i = 0;
		for (LectureInformation li : lectureInformations) {
			assertEquals(String.format("Number.%d", i + 1), li.getNumber());
			i++;
		}
	}

	@Test
	public void getSpecifigLectureInformationTest() throws AmcException {
		LectureInformation li = verifier.getLectureInformation("bla");
		assertEquals("Content", li.getContent());
		assertEquals(5, li.getEcts());
		assertEquals("Dr. Super Lecturer", li.getLecturer());
		assertEquals("Number.1", li.getNumber());
		assertEquals("SS", li.getSemester());
		assertEquals("Title", li.getTitle());

	}

	@Test()
	public void getNoLectureInformationTest() {
		try {
			verifier.getLectureInformation("blabb");
			fail();
		} catch (AmcException e) {
			assertTrue(e instanceof AmcException);
		}
	}

}