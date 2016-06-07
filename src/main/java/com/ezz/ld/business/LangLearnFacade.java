/**
 *  LangLearnFacade class is the Business Facade responsible for learning languages dynamically 
 *  
 *  - Read all files from the language samples directory
 *  - Generate n-grams from each document
 *  - Update language n-gram frequency maps based on the generated n-grams of each document
 *  - Language profiles are dynamically created based on language samples
 *  - Sort n-gram frequency map of each language based on the counts of each n-gram
 *  - Language samples should be named as: LanguageName.[n] 
 *	- Language learning is automatically started and processed once at the server start up (After the construction of the bean on the spring context)
 *	- At the end of the language learning process a list of all defined language profiles is available to be used in the language identification of any requested text
 *  
 * 
 */

package com.ezz.ld.business;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezz.ld.domain.LanguageProfile;
import com.ezz.ld.exceptions.LangDetectorException;
import com.ezz.ld.processing.LanguageProcessor;
import com.ezz.ld.processing.NGramProcessor;
import com.ezz.ld.util.ConfigReader;
import com.ezz.ld.util.FilesReader;

/**
 * @author Mohamed Ezz
 *
 */

@Component
public class LangLearnFacade {

	@Autowired
	private ConfigReader configReader;

	@Autowired
	private FilesReader filesReader;

	@Autowired
	private NGramProcessor nGramProcessor;
	
	@Autowired
	private LanguageProcessor languageProcessor;

	private List<LanguageProfile> languageProfiles;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public List<LanguageProfile> LearnLanguages() throws LangDetectorException {
		logger.debug("Learn Language based on language samples");
		languageProfiles = new ArrayList<LanguageProfile>();
		List<Path> filePaths = filesReader.getFilePaths(configReader.getLangDataPath());

		for (Path filePath : filePaths) {
			String langName = filesReader.getLanguageName(filePath);
			LanguageProfile langProfile = languageProcessor.getLanguageProfile(langName, languageProfiles);
			Map<String, Integer> nGramFreq = nGramProcessor.calculateNGramFrequency(filePath);
			languageProcessor.updateLanguageProfile(langProfile, nGramFreq);
			logger.debug(langProfile.getName());
		}

		languageProfiles.forEach(l -> l.setnGramFreq(nGramProcessor.sortNGramFreq(l.getnGramFreq())));
		return languageProfiles;
	}

	
	public List<LanguageProfile> getLanguageProfiles() {
		return languageProfiles;
	}

	public void setLanguageProfiles(List<LanguageProfile> languageProfiles) {
		this.languageProfiles = languageProfiles;
	}

}
