package com.swt.amc.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.api.UserInformation;
import com.swt.amc.api.UserInformationResponse;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IValidateCredentialsComponent;
import com.swt.amc.repositories.IUserInformationRepository;

@Component
public class ValidateCredentialsComponent implements IValidateCredentialsComponent {

	@Autowired
	private IUserInformationRepository userInfoRepo;

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
