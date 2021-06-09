package com.swt.amc.rest.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.swt.amc.interfaces.IAmcComponent;

@RestController
public class AmcRestController {

	@Autowired
	private IAmcComponent amcComponent;

	private static final Logger log = LoggerFactory.getLogger(AmcRestController.class);

	@GetMapping("/verifyQrCode/{qrCodeLink}")
	public ResponseEntity<LectureInformation> verifyQrCodeViaApp(@PathVariable("qrCodeLink") final String qrCodeLink)
			throws AmcException {
		LectureInformation lecture = amcComponent.getLectureInformation(qrCodeLink);
		return new ResponseEntity<LectureInformation>(lecture, HttpStatus.OK);
	}

	@GetMapping("/verifyQrCodeNoApp/{qrCodeLink}")
	public RedirectView verifyQrCode(@PathVariable("qrCodeLink") final String qrCodeLink) throws AmcException {
		return new RedirectView(amcComponent.getRedirectLink(qrCodeLink));
	}

	@GetMapping("/search/{searchString}")
	public ResponseEntity<List<LectureInformation>> search(@PathVariable("searchString") final String searchString)
			throws AmcException {
		return new ResponseEntity<List<LectureInformation>>(amcComponent.findLecturesBySearchString(searchString),
				HttpStatus.OK);
	}

	@GetMapping("/filterLectureInformation/{title}")
	public ResponseEntity<LectureInformation> getFilteredLectureInformation(@PathVariable("title") final String title)
			throws AmcException {

		LectureInformation lectureInformation = amcComponent.filterLectureByTitle(title);
		if (lectureInformation != null) {
			return new ResponseEntity<LectureInformation>(lectureInformation, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<UserInformationResponse> getUserInfo(
			@RequestBody UsernamePasswordInformation usernameAndPassword) throws AmcException {
		return new ResponseEntity<UserInformationResponse>(amcComponent.getUserInformationForUsernameAndPassword(
				usernameAndPassword.getUsername(), usernameAndPassword.getPassword()), HttpStatus.OK);
	}

	@GetMapping(value = "/generateQrCode/{tag}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> generateQrCode(@PathVariable("tag") final String tag) throws AmcException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(amcComponent.generateQrCode("http://192.168.1.10:8082/verifyQrCodeNoApp/" + tag), "png",
					baos);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<byte[]>(baos.toByteArray(), HttpStatus.OK);
	}

	@ExceptionHandler({ AmcException.class })
	public ResponseEntity<String> handleAmcRestException(AmcException ex, WebRequest request) {
		log.error(ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
