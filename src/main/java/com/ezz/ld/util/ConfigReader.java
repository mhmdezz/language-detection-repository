/**
 * PropertiesReader class is responsible for reading the config properties on config.properties
 */
package com.ezz.ld.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezz.ld.exceptions.LangDetectorException;


/**
 * @author Mohamed Ezz
 *
 */

@Component
public class ConfigReader {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String CONFIG_FILE_NAME = "config.properties";
	private final String NGRAM_MIN_SIZE = "ngram.minsize";
	private final String NGRAM_MAX_SIZE = "ngram.maxsize";
	private final String NGRAM_LIMIT = "ngram.limit";
	private final String LANG_DATA_PATH = "langDataPath";

	private final Properties configProp = new Properties();

	public ConfigReader() throws LangDetectorException {
		logger.info("Initializing the properties reader");
		File file = new File(this.CONFIG_FILE_NAME);
		try (FileInputStream fileInput = new FileInputStream(file)) {

			configProp.load(fileInput);

		} catch (IOException e) {
			throw new LangDetectorException(e.getMessage());
		}
	}

	public String getProperty(String key) {
		return configProp.getProperty(key);
	}

	
	public String getLangDataPath() {
		return this.getProperty(this.LANG_DATA_PATH);
	}
	
	public int getNGramMinSize() {
		return Integer.parseInt(this.getProperty(this.NGRAM_MIN_SIZE));
	}
	
	public int getNGramMaxSize() {
		return Integer.parseInt(this.getProperty(this.NGRAM_MAX_SIZE));
	}
	
	public int getNGramLimit() {
		return Integer.parseInt(this.getProperty(this.NGRAM_LIMIT));
	}
	
}
