package com.swt.amc.interfaces;

import com.swt.amc.api.LectureInformation;

public interface IFilterLectureComponent {

	LectureInformation filterLectureWithTitle(String searchTerm);

}
