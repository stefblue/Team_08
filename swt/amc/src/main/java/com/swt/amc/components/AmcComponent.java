package com.swt.amc.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.api.UserInformation;
import com.swt.amc.api.UserInformationResponse;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IAmcComponent;
import com.swt.amc.repositories.ILectureInformationRepository;
import com.swt.amc.repositories.IUserInformationRepository;

@Component
public class AmcComponent implements IAmcComponent {

	@Autowired
	private ILectureInformationRepository lectureInfoRepo;

	@Autowired
	private IUserInformationRepository userInfoRepo;

	@Override
	public LectureInformation filterLectureByTitle(String title) {
		return lectureInfoRepo.findByTitle(title);
	}

	@Override
	public String getRedirectLink(String qrCodeTag) throws AmcException {

		LectureInformation lectureInformation = lectureInfoRepo.findByTag(qrCodeTag);
		if (lectureInformation != null && lectureInformation.getTag().equals(qrCodeTag)) {
			return lectureInformation.getLink();
		}
		throw new AmcException("No lecture Information found for tag: " + qrCodeTag);
	}

	@Override
	public LectureInformation getLectureInformation(String qrCodeTag) throws AmcException {
		LectureInformation lectureInformation = lectureInfoRepo.findByTag(qrCodeTag);
		if (lectureInformation != null && lectureInformation.getTag().equals(qrCodeTag)) {
			return lectureInformation;
		}

		throw new AmcException("No lecture Information found for tag: " + qrCodeTag);
	}

	@Override
	public List<LectureInformation> findLecturesBySearchString(String searchString) {
		return lectureInfoRepo.findByTitleContaining(searchString);
	}

	@Override
	public UserInformationResponse getUserInformationForUsernameAndPassword(String username, String password)
			throws AmcException {

		UserInformation findByUserName = userInfoRepo.findByUsername(username);
		if (findByUserName != null && findByUserName.getPassword().equals(password)) {
			return new UserInformationResponse(findByUserName.getGivenName(), findByUserName.getLastName());
		}

		throw new AmcException("Invalid username or password!");
	}
}
