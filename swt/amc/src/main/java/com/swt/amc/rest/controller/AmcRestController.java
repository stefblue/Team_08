package com.swt.amc.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.api.UserInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;
import com.swt.amc.interfaces.IValidateCredentialsComponent;

@RestController
public class AmcRestController {

	@Autowired
	private IQRCodeLinkVerifier qrCodeLinkVerifier;

	@Autowired
	private IValidateCredentialsComponent validateCredentialsComponent;

	@GetMapping("/verifyQrCode/{qrCodeLink}")
	public ResponseEntity<LectureInformation> verifyQrCodeViaApp(@PathVariable("qrCodeLink") final String qrCodeLink)
			throws AmcException {
		return new ResponseEntity<LectureInformation>(qrCodeLinkVerifier.getLectureInformation(qrCodeLink),
				HttpStatus.OK);
	}

	@GetMapping("/verifyQrCodeNoApp/{qrCodeLink}")
	public RedirectView verifyQrCode(@PathVariable("qrCodeLink") final String qrCodeLink) throws AmcException {
		return new RedirectView(qrCodeLinkVerifier.getRedirectLink(qrCodeLink));
	}

	@GetMapping("/login/{userName}/{password}")
	public ResponseEntity<UserInformation> getUserInfo(@PathVariable("userName") final String userName,
			@PathVariable("password") final String password) throws AmcException {
		return new ResponseEntity<UserInformation>(
				validateCredentialsComponent.getUserInformationForUsernameAndPassword(userName, password),
				HttpStatus.OK);
	}

	@ExceptionHandler({ AmcException.class })
	public ResponseEntity<String> handleAmcRestException(AmcException ex, WebRequest request) {

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
