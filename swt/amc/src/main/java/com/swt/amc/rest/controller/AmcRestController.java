package com.swt.amc.rest.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

import com.swt.amc.api.LectureDate;
import com.swt.amc.api.LectureInformation;
import com.swt.amc.api.UserInformationResponse;
import com.swt.amc.api.UsernamePasswordInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IAmcComponent;
import com.swt.amc.repositories.ILectureInformationRepository;

@RestController
public class AmcRestController {

	@Autowired
	private IAmcComponent amcComponent;

	@Autowired
	private ILectureInformationRepository repo;

	private static final Logger log = LoggerFactory.getLogger(AmcRestController.class);

	@GetMapping("/verifyQrCode/{qrCodeLink}")
	public ResponseEntity<LectureInformation> verifyQrCodeViaApp(@PathVariable("qrCodeLink") final String qrCodeLink)
			throws AmcException {
		LectureInformation lecture = amcComponent.getLectureInformation(qrCodeLink);
		return new ResponseEntity<LectureInformation>(lecture, HttpStatus.OK);
	}

	@GetMapping("/createEntityEntry")
	public ResponseEntity<Long> CreateEntityEntry() {

		List<LectureDate> lectureDates = new ArrayList<LectureDate>();

		LectureDate lectureDate = new LectureDate(LocalDateTime.now().minus(Duration.ofHours(24)), Duration.ofHours(2));
		lectureDates.add(lectureDate);
		lectureDate = new LectureDate(LocalDateTime.now().minus(Duration.ofHours(3)), Duration.ofHours(2));
		lectureDates.add(lectureDate);
		lectureDate = new LectureDate(LocalDateTime.now().plus(Duration.ofHours(5)), Duration.ofHours(2));
		lectureDates.add(lectureDate);
		lectureDate = new LectureDate(LocalDateTime.now().plus(Duration.ofHours(7)), Duration.ofHours(2));
		lectureDates.add(lectureDate);

		LectureInformation save = repo.save(new LectureInformation("bla", "bla", "Number.1", "SS", 5,
				Collections.singleton("Dr. Super Lecturer"), "Content", "https://bla1.com", lectureDates));
		return new ResponseEntity<Long>(save.getId(), HttpStatus.CREATED);
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
			return new ResponseEntity<LectureInformation>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<UserInformationResponse> getUserInfo(
			@RequestBody UsernamePasswordInformation usernameAndPassword) throws AmcException {
		return new ResponseEntity<UserInformationResponse>(amcComponent.getUserInformationForUsernameAndPassword(
				usernameAndPassword.getUsername(), usernameAndPassword.getPassword()), HttpStatus.OK);
	}

	@ExceptionHandler({ AmcException.class })
	public ResponseEntity<String> handleAmcRestException(AmcException ex, WebRequest request) {
		log.error(ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
