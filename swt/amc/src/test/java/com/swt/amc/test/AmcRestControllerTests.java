package com.swt.amc.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.swt.amc.Amc;
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
				Collections.singletonList("Dr. Super Lecturer"), "Content", "https://bla.com"));
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

		Assert.assertTrue(lecture.getDates().getFirst() instanceof Pair);
		Assert.assertTrue(lecture.getDates().getFirst().getFirst() instanceof LocalDateTime);
		Assert.assertTrue(lecture.getDates().getFirst().getSecond() instanceof Duration);
	}

	@Test
	public void getFilteredLectureInformationTest() throws Exception {
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
		assertEquals(lectureInformationResponse.getLecturer().get(0), "Dr. Super Lecturer");
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
