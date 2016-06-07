package com.ezz.ld.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
