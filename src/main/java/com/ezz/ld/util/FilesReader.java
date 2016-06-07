/**
 * WordProcessor class is responsible for reading documents
 */
package com.ezz.ld.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezz.ld.exceptions.LangDetectorException;

/**
 * @author Mohamed Ezz
 *
 */

@Component
public class FilesReader {
	
	private static final String TEXT_REGEX = "[^ 'A-Za-z]+";
	private static final String SPACE = "\\s+";
	private static final String DOT = "\\.";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<String> readFile (Path filePath) throws LangDetectorException{
		logger.debug("Read file path: "+ filePath);
		List<String> words;
		try (Stream<String> lines = Files.lines(filePath)) {
			words = lines.map(line -> line.replaceAll(TEXT_REGEX, "").split(SPACE) ).flatMap(Arrays::stream).collect(Collectors.toList());
		} catch (IOException e) {
			throw new LangDetectorException(e.getMessage());
		}
		return words;
	}
	
	
	public List<Path> getFilePaths (String directoryPath) throws LangDetectorException{
		List<Path> filePaths = null;
		try {
			filePaths = Files.walk(Paths.get(directoryPath)).filter(f -> Files.isRegularFile(f)).collect(Collectors.toList());
		} catch (IOException e) {
			throw new LangDetectorException(e.getMessage());
		}
		return filePaths;
	}
	
	
	public List<String> getWords(String inputStr){
		return Arrays.asList(inputStr.replaceAll(TEXT_REGEX, "").split(SPACE));
	}
	
	
	public String getLanguageName(Path filePath) {
		String fileName = filePath.getFileName().toString();
		String languageName = fileName.split(DOT)[0];
		return languageName;
	}
	
}
