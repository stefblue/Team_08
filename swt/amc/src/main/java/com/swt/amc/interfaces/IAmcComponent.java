package com.swt.amc.interfaces;

import java.util.List;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.api.UserInformationResponse;
import com.swt.amc.exceptions.AmcException;

public interface IAmcComponent {

	LectureInformation filterLectureByTitle(String title);

	String getRedirectLink(String qrCodeLink) throws AmcException;

	LectureInformation getLectureInformation(String qrCodeTag) throws AmcException;

	List<LectureInformation> findLecturesBySearchString(String searchString);

	UserInformationResponse getUserInformationForUsernameAndPassword(String username, String password)
			throws AmcException;

}
