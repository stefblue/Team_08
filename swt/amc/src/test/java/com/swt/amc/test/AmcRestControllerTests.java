package com.swt.amc.test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.repositories.ILectureInformationRepository;
import com.swt.amc.rest.controller.AmcRestController;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AmcRestControllerTests {

	@Autowired
	private AmcRestController rest;

	@Autowired
	private ILectureInformationRepository lectureInformationRepo;

	@BeforeAll
	public void setUpRepo() {
		lectureInformationRepo.deleteAll();
		lectureInformationRepo.save(new LectureInformation("bla", "Title", "Number.1", "SS", 5,
				Collections.singletonList("Dr. Super Lecturer"), "Content", "https://bla.com"));
	}

	@Test
	public void verifyQrCodeViaApp_containsElements() throws AmcException {
		LectureInformation lecture = rest.verifyQrCodeViaApp("bla").getBody();

		Assert.assertEquals(5, lecture.getDates().size());
	}

	@Test
	public void verifyQrCodeViaApp_elementsAreDates() throws AmcException {
		LectureInformation lecture = rest.verifyQrCodeViaApp("bla").getBody();

		Assert.assertTrue(lecture.getDates().getFirst() instanceof Pair);
		Assert.assertTrue(lecture.getDates().getFirst().getFirst() instanceof LocalDateTime);
		Assert.assertTrue(lecture.getDates().getFirst().getSecond() instanceof Duration);
	}
}
