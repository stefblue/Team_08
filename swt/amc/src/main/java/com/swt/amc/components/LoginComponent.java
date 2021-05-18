package com.swt.amc.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swt.amc.configuration.AmcConfiguration;
import com.swt.amc.interfaces.ILoginComponent;

@Component
public class LoginComponent implements ILoginComponent {

	@Autowired
	private AmcConfiguration amcConfig;

	@Override
	public boolean isValidAuthentication(String userName, String password) {

		if (amcConfig.getUserNamePasswordMap().containsKey(userName)) {
			String storedPassword = amcConfig.getUserNamePasswordMap().get(userName);
			return storedPassword.equals(password);
		}
		return false;
	}

}
