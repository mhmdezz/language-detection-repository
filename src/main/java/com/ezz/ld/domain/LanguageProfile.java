package com.ezz.ld.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
* LanguageProfile domain bean contains contains language name and n-gram frequency map
* n-gram frequency map : counts of each n-gram in generated from sample files of each language
* The n-gram frequency map is excluded from the language profile objects while being transformed into JSON object in the REST response 
*
* @author  Mohamed Ezz
* @version 1.0
* @since   2016-06-07
*/

public class LanguageProfile {

	private String name;

	@JsonIgnore
	private Map<String, Integer> nGramFreq;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Integer> getnGramFreq() {
		return nGramFreq;
	}

	public void setnGramFreq(Map<String, Integer> nGramFreq) {
		this.nGramFreq = nGramFreq;
	}

}
