package com.ezz.ld.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezz.ld.business.LangDetectFacade;
import com.ezz.ld.domain.LanguageProfile;
import com.ezz.ld.exceptions.LangDetectorException;

@RestController
public class LangDetectResource {

	@Autowired
	private LangDetectFacade facade;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String ERROR_MESSAGE = "Please Contact Your Administrator";

	@RequestMapping(value = "/langdetect", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> detectLanguage(@RequestBody String inputText) {
		logger.debug("Detect Language for Input text: " + inputText);
		ResponseEntity<?> response;
		try {
			LanguageProfile detectedLanguage = facade.detectLanguage(inputText);
			response = new ResponseEntity<>(detectedLanguage, HttpStatus.OK);
		} catch (LangDetectorException e) {
			logger.error(e.getStackTrace().toString());
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_MESSAGE);
		}
		logger.debug("Response: " + response.getBody());
		return response;
	}

}
