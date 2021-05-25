package com.swt.amc.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.api.UserInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IValidateCredentialsComponent;
import com.swt.amc.repositories.IUserInformationRepository;

@Component
public class ValidateCredentialsComponent implements IValidateCredentialsComponent {

	@Autowired
	private IUserInformationRepository userInfoRepo;

	@Override
	public UserInformation getUserInformationForUsernameAndPassword(String userName, String password)
			throws AmcException {

		UserInformation findByUserName = userInfoRepo.findByUserName(userName);
		if (findByUserName != null && findByUserName.getPassword().equals(password)) {
			return findByUserName;
		}

		throw new AmcException("Invalid username or password!");
	}

}
