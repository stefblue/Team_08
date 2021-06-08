package com.swt.amc.interfaces;

import java.util.List;

import com.swt.amc.api.LectureInformation;

public interface ISearchLecturesComponent {

	List<LectureInformation> findLecturesBySearchString(String searchString);

}
