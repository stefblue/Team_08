package com.swt.amc.components;

import org.springframework.stereotype.Component;

import com.swt.amc.api.UserInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IValidateCredentialsComponent;

@Component
public class ValidateCredentialsComponent implements IValidateCredentialsComponent {

	@Override
	public boolean isValidAuthentication(String userName, String password) {
		return false;
	}

	@Override
	public UserInformation getUserInformationForUsernameAndPassword(String userName, String password)
			throws AmcException {
		return null;
	}

}
