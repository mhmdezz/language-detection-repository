package com.ezz.ld.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezz.ld.business.LangLearnFacade;
import com.ezz.ld.domain.LanguageProfile;

@RestController
public class LangLearnResource {

	@Autowired
	private LangLearnFacade facade;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/languages", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> availableLanguages() {
		logger.debug("Get available languages");
		ResponseEntity<List<LanguageProfile>> response;
		List<LanguageProfile> availableLanguages = facade.getLanguageProfiles();
		response = new ResponseEntity<>(availableLanguages, HttpStatus.OK);
		logger.debug("Response: " + response.getBody());
		return response;

	}

}
