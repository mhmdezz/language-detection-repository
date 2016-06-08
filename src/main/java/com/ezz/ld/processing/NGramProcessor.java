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
* N-Gram Generator class responsible for:
*   -Calculate n-gram frequency map of a a file content, text or list of words 
*   	(n-gram frequency map : counts of each n-gram in each word
*   -Generate n-gram elements from a string with different sizes 
* 	-Measure the difference between n-gram frequency maps 
* 		(n-gram frequency map of Input text - n-gram frequency map of a language profile)
* 		The less difference between text and language profile, the more probability that text belongs to this language
* 	-Sort n-gram frequency map based on the counts of of n-gram elements 
* 	-Concatenate n-gram elements to the n-gram frequency map
* 
* @author  Mohamed Ezz
* @version 1.0
* @since   2016-06-07
*/

@Component
public class NGramProcessor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ConfigReader configReader;

	@Autowired
	private FilesReader filesReader;

	
	/**
	 * Calculate n-gram frequency map of a a file content (counts of each n-gram in each word in the file) 
	 * @param  filePath	file path of document to be divided into n-grams
	 * @return  HashMap of n-gram elements and counts of each n-gram
	 */
	public HashMap<String, Integer> calculateNGramFrequency(Path filePath) throws LangDetectorException {
		List<String> words = filesReader.readFile(filePath);
		return this.calculateNGramFrequency(words);
	}

	
	/**
	 * Calculate n-gram frequency map of a string (counts of each n-gram in each word in input string) 
	 * @param  filePath	file path of document to be divided into n-grams
	 * @return  HashMap of n-gram elements and counts of each n-gram
	 */
	public HashMap<String, Integer> calculateNGramFrequency(String inputStr) throws LangDetectorException {
		List<String> words = filesReader.getWords(inputStr);
		return this.calculateNGramFrequency(words);
	}

	
	/**
	 * Calculate n-gram frequency map of a list of words (counts of each n-gram in each word) 
	 * @param  words	list of words to be divided into n-grams
	 * @return  HashMap of n-gram elements and counts of each n-gram
	 */
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
	
	
	
	/**
	 * Generate n-gram elements from a string with different sizes 
	 * @param  word		word to be divided into n-grams
	 * @param  minsize	minimum size of n-grams (number of characters for each n-gram)
	 * @param  maxSize	maximum size of n-grams (number of characters for each n-gram)
	 * @return      list of n-gram elements
	 */
	public List<String> generateNGrams(String word, int minSize, int maxSize) {
		List<String> nGramList = new ArrayList<>();
		for (int size = minSize; size <= maxSize; size++)
			addNgramsOfWord(word,size,nGramList);
		return nGramList;
	}

	
	/**
	 * Generate n-gram elements from a word and Add them to nGramList
	 * 
	 * @param  word		word to be divided into ngrams
	 * @param  size		size of n-grams (number of characters for each n-gram)
	 * @param  nGramList	n-grams list created from word
	 * 
	 */
	public void addNgramsOfWord(String word, int size, List<String> nGramList) {
		word = "_".concat(word).concat("_");
		size = size > word.length() ? word.length() : size;
		for (int i = 0; i < word.length() && i + size <= word.length(); i++) {
			nGramList.add(word.substring(i, i + size));
		}
	}
	
	
	
	/**
	 * Measure the difference between n-gram frequency maps 
	 * n-gram frequency map of Input text - n-gram frequency map of a language profile
	 * The less difference between text and language profile, the more probability that text belongs to this language 
	 * 
	 * @param  inputNGramFreq	n-gram frequency of input text
	 * @param  langNGramFreq	n-gram frequency of language profile
	 * @return	difference between frequency maps
	 */
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
	
	
	
	/**
	 * Sort n-gram frequency map based on the counts of of n-gram elements
	 * @param  nGramFreqMap		n-gram elements map
	 * @return      the sorted LinkedHashMap of n-gram frequencies
	 */
	public Map<String, Integer> sortNGramFreq(Map<String, Integer> nGramFreqMap) {
		logger.info("Sorting Map");
		return nGramFreqMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.limit(configReader.getNGramLimit())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
	
	
	/**
	 * Concatenate n-gram elements to the n-gram frequency map
	 * 
	 * @param  nGram	n-gram element (part of word)
	 * @param  count	number of n-gram counts to be added\
	 * @param  nGramFrequency	n-gram frequency map
	 * 
	 */
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
