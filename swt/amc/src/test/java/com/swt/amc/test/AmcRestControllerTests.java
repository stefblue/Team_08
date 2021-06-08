package com.swt.amc.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swt.amc.Amc;
import com.swt.amc.api.LectureDate;
import com.swt.amc.api.LectureInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.repositories.ILectureInformationRepository;
import com.swt.amc.rest.controller.AmcRestController;

@SpringBootTest(classes = Amc.class)
@TestInstance(Lifecycle.PER_CLASS)
@WebAppConfiguration
public class AmcRestControllerTests {

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	private AmcRestController rest;

	@Autowired
	private ILectureInformationRepository lectureInformationRepo;

	private MockMvc mockService;

	@BeforeAll
	public void setUpTestClass() {
		lectureInformationRepo.deleteAll();
		lectureInformationRepo.save(new LectureInformation("bla", "Title", "Number.1", "SS", 5,
				Collections.singleton("Dr. Super Lecturer"), "Content", "https://bla.com", Collections.singletonList(
						new LectureDate(LocalDateTime.now().minus(Duration.ofHours(24)), Duration.ofHours(2)))));
		mockService = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void verifyQrCodeViaApp_containsElements() throws AmcException {
		LectureInformation lecture = rest.verifyQrCodeViaApp("bla").getBody();

		assertEquals(5, lecture.getDates().size());
	}

	@Test
	public void verifyQrCodeViaApp_elementsAreDates() throws AmcException {
		LectureInformation lecture = rest.verifyQrCodeViaApp("bla").getBody();

		Assert.assertTrue(lecture.getDates().get(0).getTime() instanceof LocalDateTime);
		Assert.assertTrue(lecture.getDates().get(0).getDuration() instanceof Duration);
	}

	@Test
	public void getFilteredLectureInformationTest() throws JsonProcessingException, Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult result = mockService.perform(MockMvcRequestBuilders.get("/filterLectureInformation/Title"))
				.andReturn();
		LectureInformation lectureInformationResponse = objectMapper
				.readValue(result.getResponse().getContentAsString(), LectureInformation.class);

		assertNotNull(lectureInformationResponse);
		assertEquals(lectureInformationResponse.getTitle(), "Title");
		assertEquals(lectureInformationResponse.getNumber(), "Number.1");
		assertEquals(lectureInformationResponse.getSemester(), "SS");
		assertEquals(lectureInformationResponse.getEcts(), 5);
		assertEquals(lectureInformationResponse.getLecturer().iterator().next(), "Dr. Super Lecturer");
		assertEquals(lectureInformationResponse.getContent(), "Content");
	}
}
