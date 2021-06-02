package com.swt.amc.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.interfaces.IFilterLectureComponent;
import com.swt.amc.repositories.ILectureInformationRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class FilterLectureTest {

	@Autowired
	private ILectureInformationRepository lectureInformationRepo;

	@Autowired
	private Optional<IFilterLectureComponent> filterLectureComponent;

	@BeforeAll
	public void setUpRepo() {
		lectureInformationRepo.deleteAll();
		lectureInformationRepo.save(new LectureInformation("bla", "Title", "Number.1", "SS", 5,
				Collections.singletonList("Dr. Super Lecturer"), "Content", "https://bla.com"));
	}

	@Test
	public void testFilter() {
		assertTrue(filterLectureComponent.isPresent());
		LectureInformation filteredLecture = filterLectureComponent.get().filterLectureByTitle("Title");
		assertNotNull(filteredLecture);
		assertEquals(filteredLecture.getContent(), "Content");
		assertEquals(filteredLecture.getTitle(), "Title");
		assertEquals(filteredLecture.getTag(), "bla");
	}

}
