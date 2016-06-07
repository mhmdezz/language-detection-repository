/**
 *  LangDetectFacade class is the Business Facade responsible for detecting the language 
 *  of input string.
 *  
 *  - Get all available Language Profiles from LangLearnFacade
 *  - Use NGramProcessor for calculating the n-gram frequency map of the input text
 *  - Sort the n-gram frequency map of the input text based on the counts of each n-gram
 *  - Use NGramProcessor for measuring the difference between n-gram frequency map of input text and 
 *  	all other languages
 * 	- Return the language with minimum n-gram frequency difference
 *  
 * 
 */


package com.ezz.ld.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezz.ld.domain.LanguageProfile;
import com.ezz.ld.exceptions.LangDetectorException;
import com.ezz.ld.processing.NGramProcessor;
import com.ezz.ld.util.ConfigReader;

@Component
public class LangDetectFacade {

	@Autowired
	private LangLearnFacade langLearnFacade;

	@Autowired
	private NGramProcessor nGramProcessor;

	@Autowired
	ConfigReader configReader;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public LanguageProfile detectLanguage(String inputStr) throws LangDetectorException {
		List<LanguageProfile> languageProfiles = langLearnFacade.getLanguageProfiles();
		LanguageProfile languageProfile = this.chooseLangProfile(inputStr, languageProfiles);
		return languageProfile;
	}

	private LanguageProfile chooseLangProfile(String inputStr, List<LanguageProfile> languageProfiles)
			throws LangDetectorException {
		Map<LanguageProfile, Long> languageProfilesDistanceMap = new HashMap<LanguageProfile, Long>();
		
		//Calculate the n-gram frequency map of the input text
		Map<String, Integer> inputNGramFreq = nGramProcessor.calculateNGramFrequency(inputStr);
		
		//Sort the n-gram frequency map of the input text based on the counts of each n-gram
		inputNGramFreq = nGramProcessor.sortNGramFreq(inputNGramFreq);
		
		//Measure the difference between n-gram frequency map of input text and all other languages
		for (LanguageProfile langProfile : languageProfiles) {
			logger.info("Measure diff from language profile: " + langProfile.getName());
			Map<String, Integer> langNGramFreq = langProfile.getnGramFreq();
			Long nGramFreqDiff = nGramProcessor.measureNGramFreqDiff(inputNGramFreq, langNGramFreq);
			logger.debug("Diff from language profile: " + langProfile.getName() + " : " + nGramFreqDiff);
			languageProfilesDistanceMap.put(langProfile, nGramFreqDiff);
		}
		
		//Return the language with minimum n-gram frequency difference 
		return languageProfilesDistanceMap.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
	}

}
