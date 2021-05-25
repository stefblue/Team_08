package com.swt.amc.interfaces;

import com.swt.amc.api.UserInformation;
import com.swt.amc.exceptions.AmcException;

public interface IValidateCredentialsComponent {

	boolean isValidAuthentication(String userName, String password);

	UserInformation getUserInformationForUsernameAndPassword(String userName, String password) throws AmcException;

}
