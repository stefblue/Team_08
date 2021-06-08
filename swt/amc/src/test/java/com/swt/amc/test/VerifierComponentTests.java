package com.swt.amc.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
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
import org.springframework.boot.test.context.SpringBootTest;

import com.swt.amc.api.LectureDate;
import com.swt.amc.api.LectureInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IAmcComponent;
import com.swt.amc.repositories.ILectureInformationRepository;

@SpringBootTest()
@TestInstance(Lifecycle.PER_CLASS)
public class VerifierComponentTests {

	@Autowired
	private IAmcComponent amcComponent;

	@Autowired
	private ILectureInformationRepository lectureInformationRepo;

	private List<String> testEntries = Arrays.asList("bla", "bli", "blubb");

	@BeforeAll
	public void setUpRepo() {
		lectureInformationRepo.deleteAll();

		lectureInformationRepo.save(new LectureInformation("bla", "Title", "Number.1", "SS", 5,
				Collections.singleton("Dr. Super Lecturer"), "Content", "https://bla.com", Collections.singletonList(
						new LectureDate(LocalDateTime.now().minus(Duration.ofHours(24)), Duration.ofHours(2)))));
		lectureInformationRepo.save(new LectureInformation("bli", "Title2", "Number.2", "WS", 1,
				Collections.singleton("L1"), "Content2", "https://bli.com", Collections.singletonList(
						new LectureDate(LocalDateTime.now().minus(Duration.ofHours(24)), Duration.ofHours(2)))));
		lectureInformationRepo.save(new LectureInformation("blubb", "Title3", "Number.3", "WS/SS", 1,
				Collections.singleton("Lecturer3"), "Content3", "https://blubb.com", Collections.singletonList(
						new LectureDate(LocalDateTime.now().minus(Duration.ofHours(24)), Duration.ofHours(2)))));
	}

	@Test
	public void getLinkTest() throws AmcException {
		List<String> redirectUrls = new ArrayList<String>();
		for (String testEntry : testEntries) {
			redirectUrls.add(amcComponent.getRedirectLink(testEntry));
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
			amcComponent.getRedirectLink("blabb");
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AmcException);
		}
	}

	@Test
	public void getLectureInformationTest() throws AmcException {
		List<LectureInformation> lectureInformations = new ArrayList<LectureInformation>();
		for (String testEntry : testEntries) {
			lectureInformations.add(amcComponent.getLectureInformation(testEntry));
		}
		int i = 0;
		for (LectureInformation li : lectureInformations) {
			assertEquals(String.format("Number.%d", i + 1), li.getNumber());
			i++;
		}
	}

	@Test
	public void getSpecifigLectureInformationTest() throws AmcException {
		LectureInformation li = amcComponent.getLectureInformation("bla");
		assertEquals("Content", li.getContent());
		assertEquals(5, li.getEcts());
		assertEquals("Dr. Super Lecturer", li.getLecturer().iterator().next());
		assertEquals("Number.1", li.getNumber());
		assertEquals("SS", li.getSemester());
		assertEquals("Title", li.getTitle());

	}

	@Test()
	public void getNoLectureInformationTest() {
		try {
			amcComponent.getLectureInformation("blabb");
			fail();
		} catch (AmcException e) {
			assertTrue(e instanceof AmcException);
		}
	}

}