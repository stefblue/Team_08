package com.swt.amc.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.swt.amc.Amc;
import com.swt.amc.api.LectureDate;
import com.swt.amc.api.LectureInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.repositories.ILectureInformationRepository;
import com.swt.amc.rest.controller.AmcRestController;

@SpringBootTest(classes = Amc.class)
@TestInstance(Lifecycle.PER_CLASS)
@WebAppConfiguration
public class AmcRestControllerTests extends AbstractTest {

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
		List<LectureDate> lectureDates = new ArrayList<LectureDate>();

		Date date = new Date();

		LectureDate lectureDate = new LectureDate(addHoursToDate(date, -24), Duration.ofHours(2));
		lectureDates.add(lectureDate);
		lectureDate = new LectureDate(addHoursToDate(date, -3), Duration.ofHours(2));
		lectureDates.add(lectureDate);
		lectureDate = new LectureDate(addHoursToDate(date, -5), Duration.ofHours(2));
		lectureDates.add(lectureDate);
		lectureDate = new LectureDate(addHoursToDate(date, -7), Duration.ofHours(2));
		lectureDates.add(lectureDate);
		lectureDate = new LectureDate(addHoursToDate(date, 9), Duration.ofHours(2));
		lectureDates.add(lectureDate);

		lectureInformationRepo.save(new LectureInformation("bla", "Title", "Number.1", "SS", 5,
				Collections.singleton("Dr. Super Lecturer"), "Content", "https://bla.com", lectureDates));
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

		Assert.assertTrue(lecture.getDates().get(0).getTime() instanceof Date);
		Assert.assertTrue(lecture.getDates().get(0).getDuration() instanceof Duration);
	}

	@Test
	public void getFilteredLectureInformationTest() throws Exception {

		ObjectMapper objectMapper = JsonMapper.builder().addModule(new ParameterNamesModule())
				.addModule(new Jdk8Module()).addModule(new JavaTimeModule()).build();

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

	@Test
	public void getQrCodeTest() throws Exception {
		MvcResult result = mockService.perform(MockMvcRequestBuilders.get("/generateQrCode/testTag")).andReturn();
		result.getResponse().getContentAsByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(result.getResponse().getContentAsByteArray());
		BufferedImage qrCode = ImageIO.read(bais);
		LuminanceSource ls = new BufferedImageLuminanceSource(qrCode);
		BinaryBitmap bm = new BinaryBitmap(new HybridBinarizer(ls));
		Result qrCodeContent = new MultiFormatReader().decode(bm);
		assertEquals(qrCodeContent.getText(), "http://192.168.1.10:8082/verifyQrCodeNoApp/testTag");
	}
}
