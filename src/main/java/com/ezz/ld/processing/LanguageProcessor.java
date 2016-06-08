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
* Language processor class responsible for:
* 
* 	-Get the language profile object based on the language name
* 	-Create a new language profile if the language was not exist, and add the new profile to the languages list
* 	-Update language profiles with n-gram frequency maps
* 	-Create a new map if the language profile is empty
*	-Concatenate the new map to the existing map of a language profile using the n-gram processor
*  
* @author  Mohamed Ezz
* @version 1.0
* @since   2016-06-07
*/

@Component
public class LanguageProcessor {
	
	@Autowired
	NGramProcessor nGramProcessor;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	/**
	 * Update language profiles with n-gram frequency maps
	 * Create a new map if the language profile is empty
	 * Concatenate the new map to the existing map of a language profile
	 *  
	 * @param  newMap		new n-grams frequency map to be added/updated to the language profile
	 * @param  langProfile	language profile 
	 *
	 */
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
	
	
	/**
	 * Get the language profile object based on the language name
	 * Or create a new language profile if the language was not exist, and add the new profile to the languages list
	 *
	 * @param  langName				language name
	 * @param  languageProfiles		language profiles list that contains all the dynamically detected languages 
	 * @return		Detected or new language profile object
	 *
	 */
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
