package com.swt.amc.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;
import com.swt.amc.repositories.ILectureInformationRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class QRCodeLinkVerifierComponentTests {

	@Autowired
	@Qualifier("qrCodeLinkVerifierComponent")
	private IQRCodeLinkVerifier verifier;

	@Autowired
	private ILectureInformationRepository lectureInformationRepo;

	private List<String> testEntries = Arrays.asList("bla", "bli", "blubb");

	@BeforeAll
	public void setUpRepo() {
		lectureInformationRepo.deleteAll();

		createLectureInformation("bla", "Title", "Number.1", "SS", 5, "Dr. Super Lecturer", "Content",
				"https://bla.com");
		createLectureInformation("bli", "Title2", "Number.2", "WS", 1, "L1", "Content2", "https://bli.com");
		createLectureInformation("blubb", "Title3", "Number.3", "WS/SS", 1, "Lecturer3", "Content3",
				"https://blubb.com");
	}

	private void createLectureInformation(String tag, String title, String number, String semester, Integer ects,
			String lecturer, String content, String link) {
		LectureInformation lectureInformation = new LectureInformation();
		lectureInformation.setTag(tag);
		lectureInformation.setTitle(title);
		lectureInformation.setNumber(number);
		lectureInformation.setSemester(semester);
		lectureInformation.setEcts(ects);
		lectureInformation.setLecturer(Collections.singletonList(lecturer));
		lectureInformation.setContent(content);
		lectureInformation.setLink(link);
		lectureInformationRepo.save(lectureInformation);
	}

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
		} catch (Exception e) {
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
		assertEquals("Dr. Super Lecturer", li.getLecturer().get(0));
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