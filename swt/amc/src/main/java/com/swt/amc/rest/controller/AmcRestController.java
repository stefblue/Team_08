package com.swt.amc.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.api.UserInformationResponse;
import com.swt.amc.api.UsernamePasswordInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;
import com.swt.amc.interfaces.IValidateCredentialsComponent;

@RestController
public class AmcRestController {

	@Autowired
	private IQRCodeLinkVerifier qrCodeLinkVerifier;

	@Autowired
	private IValidateCredentialsComponent validateCredentialsComponent;

	private static final Logger log = LoggerFactory.getLogger(AmcRestController.class);

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

	@PostMapping("/login")
	public ResponseEntity<UserInformationResponse> getUserInfo(
			@RequestBody UsernamePasswordInformation usernameAndPassword) throws AmcException {
		return new ResponseEntity<UserInformationResponse>(
				validateCredentialsComponent.getUserInformationForUsernameAndPassword(usernameAndPassword.getUsername(),
						usernameAndPassword.getPassword()),
				HttpStatus.OK);
	}

	@ExceptionHandler({ AmcException.class })
	public ResponseEntity<String> handleAmcRestException(AmcException ex, WebRequest request) {
		log.error(ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
