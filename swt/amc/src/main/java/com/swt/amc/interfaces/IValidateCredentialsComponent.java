package com.swt.amc.interfaces;

import com.swt.amc.api.UserInformationResponse;
import com.swt.amc.exceptions.AmcException;

public interface IValidateCredentialsComponent {

	UserInformationResponse getUserInformationForUsernameAndPassword(String username, String password)
			throws AmcException;

}
