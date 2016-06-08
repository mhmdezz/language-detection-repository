package com.ezz.ld.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezz.ld.exceptions.LangDetectorException;

public class ConfigReaderTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	ConfigReader configReader;
	
	@Test
	public void testReadProperty(){
		
		logger.info("Test Config Reader");
		boolean exceptionOccurred = false;
		
		try {
			configReader = new ConfigReader();
		} catch (LangDetectorException e) {
			exceptionOccurred = true;
		}
		
		Assert.assertFalse(exceptionOccurred);
		
		String propValue = null;
		propValue = configReader.getProperty("ngram.limit");
		
		logger.info(propValue);
		Assert.assertNotNull(propValue);
	}

}
