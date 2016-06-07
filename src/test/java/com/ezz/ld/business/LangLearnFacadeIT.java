package com.ezz.ld.business;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ezz.ld.LangDetectorApplication;
import com.ezz.ld.domain.LanguageProfile;
import com.ezz.ld.exceptions.LangDetectorException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LangDetectorApplication.class)
public class LangLearnFacadeIT {

	@Autowired
	LangLearnFacade facade;
	
	@Test
	public void testLearnLanguage(){
		
		boolean exceptionOccurred = false;
		
		try {
			
			List<LanguageProfile> languageProfiles = facade.LearnLanguages();
			Assert.assertTrue(languageProfiles.size()>0);
			Assert.assertNotNull(languageProfiles.get(0));
			Assert.assertNotNull(languageProfiles.get(0).getName());
			Assert.assertTrue(languageProfiles.get(0).getnGramFreq().size()>0);
			
			Assert.assertTrue(facade.LearnLanguages().equals(facade.getLanguageProfiles())); 
			
		} catch (LangDetectorException e) {
			exceptionOccurred = true;
			e.printStackTrace();
		}
		
		Assert.assertFalse(exceptionOccurred);
	}
	
	
}
