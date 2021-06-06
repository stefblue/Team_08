package com.swt.amc.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.interfaces.IFilterLectureComponent;
import com.swt.amc.repositories.ILectureInformationRepository;

@Component
public class FilterLectureComponent implements IFilterLectureComponent {

	@Autowired
	private ILectureInformationRepository lectureInfoRepo;

	@Override
	public LectureInformation filterLectureByTitle(String title) {
		return lectureInfoRepo.findByTitle(title);
	}
}
