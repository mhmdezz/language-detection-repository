/**
 * NGramGenerator class 
 */
package com.ezz.ld.processing;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezz.ld.exceptions.LangDetectorException;
import com.ezz.ld.util.ConfigReader;
import com.ezz.ld.util.FilesReader;

/**
 * @author Mohamed Ezz
 *
 */

@Component
public class NGramProcessor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ConfigReader configReader;

	@Autowired
	FilesReader filesReader;

	public HashMap<String, Integer> calculateNGramFrequency(Path filePath) throws LangDetectorException {
		List<String> words = filesReader.readFile(filePath);
		return this.calculateNGramFrequency(words);
	}

	public HashMap<String, Integer> calculateNGramFrequency(String inputStr) throws LangDetectorException {
		List<String> words = filesReader.getWords(inputStr);
		return this.calculateNGramFrequency(words);
	}

	public HashMap<String, Integer> calculateNGramFrequency(List<String> words) {
		HashMap<String, Integer> nGramFrequency = new HashMap<String, Integer>();
		for (String w : words) {
			List<String> nGrams = this.generateNGrams(w, configReader.getNGramMinSize(),
					configReader.getNGramMaxSize());
			nGrams.forEach(n -> this.concatNgramToFrequency(n, 1, nGramFrequency));
		}
		// nGramFrequency.forEach((k, v) -> logger.debug("n-gram : " + k + " : "
		// + v));
		return nGramFrequency;
	}

	public Long measureNGramFreqDiff(Map<String, Integer> inputNGramFreq, Map<String, Integer> langNGramFreq) {

		Long diffSum = 0l;
		List<String> inputNGramList = new LinkedList<String>();
		inputNGramFreq.forEach((k, v) -> inputNGramList.add(k));

		List<String> langNGramList = new LinkedList<String>();
		langNGramFreq.forEach((k, v) -> langNGramList.add(k));

		for (String inputNGram : inputNGramList) {
			Integer diff = 0;
			if (langNGramList.contains(inputNGram)) {
				diff = langNGramList.indexOf(inputNGram) - inputNGramList.indexOf(inputNGram);
			} else {
				diff = langNGramList.size() < configReader.getNGramLimit() ? langNGramList.size()
						: configReader.getNGramLimit();
			}
			diffSum += diff;
		}

		return diffSum;
	}

	public List<String> generateNGrams(String input, int minSize, int maxSize) {
		List<String> nGramList = new ArrayList<>();
		for (int size = minSize; size <= maxSize; size++)
			addNgrams(size, input, nGramList);
		return nGramList;
	}

	public Map<String, Integer> sortNGramFreq(Map<String, Integer> map) {
		logger.info("Sorting Map");
		return map.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.limit(configReader.getNGramLimit())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
	
	public void addNgrams(int size, String input, List<String> nGramList) {
		input = "_".concat(input).concat("_");
		size = size > input.length() ? input.length() : size;
		for (int i = 0; i < input.length() && i + size <= input.length(); i++) {
			nGramList.add(input.substring(i, i + size));
		}
	}
	
	
	public void concatNgramToFrequency(String nGram, Integer count, Map<String, Integer> nGramFrequency) {
		if (nGramFrequency.containsKey(nGram)) {
			Integer freq = (Integer) nGramFrequency.get(nGram);
			freq += count;
			nGramFrequency.put(nGram, freq);
		} else {
			nGramFrequency.put(nGram, count);
		}
	}

}
