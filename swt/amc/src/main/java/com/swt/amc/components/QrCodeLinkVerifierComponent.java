package com.swt.amc.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;
import com.swt.amc.repositories.ILectureInformationRepository;

@Component
public class QrCodeLinkVerifierComponent implements IQRCodeLinkVerifier {

	@Autowired
	private ILectureInformationRepository repository;

	@Override
	public String getRedirectLink(String qrCodeTag) throws AmcException {

		LectureInformation lectureInformation = repository.findByTag(qrCodeTag);
		if (lectureInformation != null && lectureInformation.getTag().equals(qrCodeTag)) {
			return lectureInformation.getLink();
		}
		throw new AmcException("No lecture Information found for tag: " + qrCodeTag);
	}

	@Override
	public LectureInformation getLectureInformation(String qrCodeTag) throws AmcException {
		LectureInformation lectureInformation = repository.findByTag(qrCodeTag);
		if (lectureInformation != null && lectureInformation.getTag().equals(qrCodeTag)) {
			return lectureInformation;
		}

		throw new AmcException("No lecture Information found for tag: " + qrCodeTag);

	}

}
