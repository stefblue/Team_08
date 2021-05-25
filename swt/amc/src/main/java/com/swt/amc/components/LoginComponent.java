package com.swt.amc.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.api.UserInformation;
import com.swt.amc.configuration.AmcConfiguration;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IValidateCredentialsComponent;

@Component
public class LoginComponent implements IValidateCredentialsComponent {

	@Autowired
	private AmcConfiguration amcConfig;

	@Override
	public boolean isValidAuthentication(String userName, String password) {

		if (amcConfig.getUserNamePasswordMap().containsKey(userName)) {
			String storedPassword = amcConfig.getUserNamePasswordMap().get(userName).getPassword();
			return storedPassword.equals(password);
		}
		return false;
	}

	@Override
	public UserInformation getUserInformationForUsernameAndPassword(String userName, String password)
			throws AmcException {
		if (isValidAuthentication(userName, password)) {
			return amcConfig.getUserNamePasswordMap().get(userName);
		}
		throw new AmcException("Username or password false!");
	}

}
