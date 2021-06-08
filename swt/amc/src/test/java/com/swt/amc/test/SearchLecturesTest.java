package com.swt.amc.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
public class SearchLecturesTest {

	@Autowired
	private ILectureInformationRepository lectureInformationRepo;

	@Autowired
	private Optional<IAmcComponent> amcComponent;

	@BeforeAll
	public void setUpRepo() {
		lectureInformationRepo.deleteAll();
		lectureInformationRepo.save(new LectureInformation("bla", "bla", "Number.1", "SS", 5,
				Collections.singleton("Dr. Super Lecturer"), "Content", "https://bla1.com", Collections.singletonList(
						new LectureDate(LocalDateTime.now().minus(Duration.ofHours(24)), Duration.ofHours(2)))));
		lectureInformationRepo.save(new LectureInformation("blo", "blo", "Number.2", "SS", 5,
				Collections.singleton("Dr. Super Lecturer"), "Content", "https://bla2.com", Collections.singletonList(
						new LectureDate(LocalDateTime.now().minus(Duration.ofHours(24)), Duration.ofHours(2)))));
		lectureInformationRepo.save(new LectureInformation("blu", "blu", "Number.3", "SS", 5,
				Collections.singleton("Dr. Super Lecturer"), "Content", "https://bla3.com", Collections.singletonList(
						new LectureDate(LocalDateTime.now().minus(Duration.ofHours(24)), Duration.ofHours(2)))));
		lectureInformationRepo.save(new LectureInformation("all", "all", "Number.4", "SS", 5,
				Collections.singleton("Dr. Super Lecturer"), "Content", "https://bla4.com", Collections.singletonList(
						new LectureDate(LocalDateTime.now().minus(Duration.ofHours(24)), Duration.ofHours(2)))));
	}

	@Test
	public void testSearch() {
		assertTrue(amcComponent.isPresent());
		List<LectureInformation> filteredLecture = amcComponent.get().findLecturesBySearchString("a");
		assertNotNull(filteredLecture);
		assertEquals(filteredLecture.size(), 2);
		filteredLecture = amcComponent.get().findLecturesBySearchString("bl");
		assertNotNull(filteredLecture);
		assertEquals(filteredLecture.size(), 3);
		filteredLecture = amcComponent.get().findLecturesBySearchString("l");
		assertNotNull(filteredLecture);
		assertEquals(filteredLecture.size(), 4);
	}

}
