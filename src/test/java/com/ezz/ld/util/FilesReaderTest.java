package com.ezz.ld.util;

import java.nio.file.Path;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezz.ld.exceptions.LangDetectorException;

public class FilesReaderTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	FilesReader documentReader = new FilesReader();
	
	private static final String TEST_PATH = "src/test/resources/";

	@Test
	public void testFileReader() {

		logger.info("Test Files Reader");
		boolean exceptionOccurred = false;

		try {

			List<Path> paths = documentReader.getFilePaths(TEST_PATH);
			Assert.assertTrue(paths.size()==1);
			
			String languageName = documentReader.getLanguageName(paths.get(0));
			Assert.assertTrue(languageName.equalsIgnoreCase("ENGLISH"));
			
			List<String> words = documentReader.readFile(paths.get(0));
			Assert.assertTrue(words.size()==4);
			
			List<String> words2 = documentReader.getWords("Paul McCartney is an English singer and composer");
			Assert.assertTrue(words2.size()==8);
			
		} catch (LangDetectorException e) {
			exceptionOccurred = true;
		}

		Assert.assertFalse(exceptionOccurred);

	}

}
