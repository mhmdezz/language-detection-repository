package com.ezz.ld.processing;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezz.ld.exceptions.LangDetectorException;
import com.ezz.ld.util.ConfigReader;
import com.ezz.ld.util.FilesReader;

@RunWith(MockitoJUnitRunner.class)
public class NGramProcessorTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String TEST_FILE_PATH = "src/test/resources/ENGLISH.1.txt";

	@Mock
	ConfigReader configReader;

	@Mock
	FilesReader filesReader;

	@InjectMocks
	private NGramProcessor processor;

	@Test
	public void testSortNGramFreq() {

		Map<String, Integer> nGramFreqMap = new HashMap<String, Integer>();

		Mockito.when(configReader.getNGramLimit()).thenReturn(100);

		nGramFreqMap.put("is", 2);
		nGramFreqMap.put("Art", 5);
		nGramFreqMap.put("To", 3);

		nGramFreqMap = processor.sortNGramFreq(nGramFreqMap);

		List<String> nGramFreqList = new LinkedList<String>();
		nGramFreqMap.forEach((k, v) -> nGramFreqList.add(k));

		Assert.assertTrue(nGramFreqList.indexOf("Art") == 0);
		Assert.assertTrue(nGramFreqList.indexOf("To") == 1);
		Assert.assertTrue(nGramFreqList.indexOf("is") == 2);

	}

	@Test
	public void testGenerateNGrams() {
		List<String> nGramList = processor.generateNGrams("Egypt", 2, 3);

		Assert.assertTrue(nGramList.contains("_E"));
		Assert.assertTrue(nGramList.contains("Eg"));
		Assert.assertTrue(nGramList.contains("gy"));
		Assert.assertTrue(nGramList.contains("yp"));
		Assert.assertTrue(nGramList.contains("pt"));
		Assert.assertTrue(nGramList.contains("t_"));

		Assert.assertTrue(nGramList.contains("_Eg"));
		Assert.assertTrue(nGramList.contains("Egy"));
		Assert.assertTrue(nGramList.contains("gyp"));
		Assert.assertTrue(nGramList.contains("ypt"));
		Assert.assertTrue(nGramList.contains("pt_"));

	}

	@Test
	public void testAddNgramsOfWord() {
		List<String> nGramList = new ArrayList<String>();
		processor.addNgramsOfWord("Egypt", 2, nGramList);

		Assert.assertTrue(nGramList.contains("_E"));
		Assert.assertTrue(nGramList.contains("Eg"));
		Assert.assertTrue(nGramList.contains("gy"));
		Assert.assertTrue(nGramList.contains("yp"));
		Assert.assertTrue(nGramList.contains("pt"));
		Assert.assertTrue(nGramList.contains("t_"));

	}

	@Test
	public void testConcatNgramToFrequency() {
		processor = new NGramProcessor();
		Map<String, Integer> ngramFreqMap = new HashMap<String, Integer>();
		ngramFreqMap.put("art", 1);
		ngramFreqMap.put("is", 1);

		processor.concatNgramToFrequency("is", 2, ngramFreqMap);
		processor.concatNgramToFrequency("every", 1, ngramFreqMap);

		Assert.assertTrue(ngramFreqMap.get("is") == 3);
		Assert.assertTrue(ngramFreqMap.get("art") == 1);
		Assert.assertTrue(ngramFreqMap.get("every") == 1);
	}


	@Test
	public void testCalculateNGramFrequency() {

		Path path = Paths.get(TEST_FILE_PATH);
		List<String> words = new ArrayList<String>();

		words.add("Art");
		words.add("is");
		words.add("for");
		words.add("everyone");

		try {
			Mockito.when(filesReader.readFile(path)).thenReturn(words);
			processor.calculateNGramFrequency(path);
		} catch (LangDetectorException e1) {
			e1.printStackTrace();
		}


		logger.info("Test Config Reader");
		boolean exceptionOccurred = false;

		try {
			configReader = new ConfigReader();
		} catch (LangDetectorException e) {
			exceptionOccurred = true;
		}

		Assert.assertFalse(exceptionOccurred);

	}

}
