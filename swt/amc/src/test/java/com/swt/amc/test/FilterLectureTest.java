package com.swt.amc.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.swt.amc.api.LectureDate;
import com.swt.amc.api.LectureInformation;
import com.swt.amc.interfaces.IAmcComponent;
import com.swt.amc.repositories.ILectureInformationRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class FilterLectureTest {

	@Autowired
	private ILectureInformationRepository lectureInformationRepo;

	@Autowired
	private Optional<IAmcComponent> amcComponent;

	@BeforeAll
	public void setUpRepo() {
		lectureInformationRepo.deleteAll();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR_OF_DAY, -24);
		lectureInformationRepo.save(new LectureInformation("bla", "Title", "Number.1", "SS", 5,
				Collections.singleton("Dr. Super Lecturer"), "Content", "https://bla.com",
				Collections.singletonList(new LectureDate(calendar.getTime(), Duration.ofHours(2)))));
	}

	@Test
	public void testFilter() {
		assertTrue(amcComponent.isPresent());
		LectureInformation filteredLecture = amcComponent.get().filterLectureByTitle("Title");
		assertNotNull(filteredLecture);
		assertEquals(filteredLecture.getContent(), "Content");
		assertEquals(filteredLecture.getTitle(), "Title");
		assertEquals(filteredLecture.getTag(), "bla");
	}

}
