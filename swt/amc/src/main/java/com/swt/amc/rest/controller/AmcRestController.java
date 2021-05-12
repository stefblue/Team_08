package com.swt.amc.rest.controller;

import javafx.util.Pair;
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
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IQRCodeLinkVerifier;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
public class AmcRestController {

	@Autowired
	private IQRCodeLinkVerifier qrCodeLinkVerifier;

	@GetMapping("/verifyQrCode/{qrCodeLink}")
	public ResponseEntity<LectureInformation> verifyQrCodeViaApp(@PathVariable("qrCodeLink") final String qrCodeLink)
			throws AmcException {
		LectureInformation lecture = qrCodeLinkVerifier.getLectureInformation(qrCodeLink);
		lecture.addDate(new Pair(LocalDateTime.now().minus(Duration.ofHours(24)), Duration.ofHours(2)));
		lecture.addDate(new Pair(LocalDateTime.now().minus(Duration.ofHours(3)), Duration.ofHours(2)));
		lecture.addDate(new Pair(LocalDateTime.now(), Duration.ofHours(2)));
		lecture.addDate(new Pair(LocalDateTime.now().plus(Duration.ofHours(5)), Duration.ofHours(2)));
		lecture.addDate(new Pair(LocalDateTime.now().plus(Duration.ofHours(7)), Duration.ofHours(2)));
		return new ResponseEntity(lecture, HttpStatus.OK);
	}

	@GetMapping("/verifyQrCodeNoApp/{qrCodeLink}")
	public RedirectView verifyQrCode(@PathVariable("qrCodeLink") final String qrCodeLink) throws AmcException {
		return new RedirectView(qrCodeLinkVerifier.getRedirectLink(qrCodeLink));
	}

	@ExceptionHandler({ AmcException.class })
	public ResponseEntity<String> handleAmcRestException(AmcException ex, WebRequest request) {

		return new ResponseEntity<String>("Given key not in whitelist!", HttpStatus.BAD_REQUEST);
	}
}
