package com.ezz.ld.processing;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ezz.ld.domain.LanguageProfile;

@RunWith(MockitoJUnitRunner.class)
public class LanguageProcessorTest {

	
	@Mock
	NGramProcessor nGramProcessor;
	
	@InjectMocks
	private LanguageProcessor processor;
	
	@Test
	public void testGetLanguageProfile() {

		List<LanguageProfile> languageProfiles = new ArrayList<LanguageProfile>();
		LanguageProfile languageProfile1 = new LanguageProfile();
		languageProfile1.setName("ENGLISH");
		
		LanguageProfile languageProfile2 = new LanguageProfile();
		languageProfile2.setName("SPANISH");
		
		languageProfiles.add(languageProfile1);
		languageProfiles.add(languageProfile2);
		
		LanguageProfile languageProfile = processor.getLanguageProfile("spanish", languageProfiles);
		Assert.assertTrue(languageProfile.getName().equalsIgnoreCase("spanish"));
		
	}


}
