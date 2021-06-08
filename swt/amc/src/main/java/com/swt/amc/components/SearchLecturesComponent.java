package com.swt.amc.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.interfaces.ISearchLecturesComponent;
import com.swt.amc.repositories.ILectureInformationRepository;

@Component
public class SearchLecturesComponent implements ISearchLecturesComponent {

	@Autowired
	private ILectureInformationRepository lectureInfoRepo;

	@Override
	public List<LectureInformation> findLecturesBySearchString(String searchString) {
		return lectureInfoRepo.findByTitleContaining(searchString);
	}

}
