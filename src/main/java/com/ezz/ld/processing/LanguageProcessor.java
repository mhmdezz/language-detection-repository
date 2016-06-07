/**
 * NGramGenerator class 
 */
package com.ezz.ld.processing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezz.ld.domain.LanguageProfile;

/**
 * @author Mohamed Ezz
 *
 */

@Component
public class LanguageProcessor {
	
	@Autowired
	NGramProcessor nGramProcessor;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void updateLanguageProfile(LanguageProfile langProfile, Map<String, Integer> newMap) {
		Map<String, Integer> existingMap = langProfile.getnGramFreq();

		if (null == existingMap) {
			existingMap = new HashMap<String, Integer>();
			langProfile.setnGramFreq(existingMap);
		}

		for (Map.Entry<String, Integer> entry : newMap.entrySet()) {
			nGramProcessor.concatNgramToFrequency(entry.getKey(), entry.getValue(), existingMap);
		}

	}
	
	
	public LanguageProfile getLanguageProfile(String langName, List<LanguageProfile> languageProfiles) {
		LanguageProfile langProfile;
		Optional<LanguageProfile> langProfileOptional = languageProfiles.stream()
				.filter(l -> langName.equalsIgnoreCase(l.getName())).findFirst();
		if (langProfileOptional.isPresent()) {
			langProfile = langProfileOptional.get();
		} else {
			langProfile = new LanguageProfile();
			langProfile.setName(langName);
			languageProfiles.add(langProfile);
		}
		return langProfile;
	}


}
