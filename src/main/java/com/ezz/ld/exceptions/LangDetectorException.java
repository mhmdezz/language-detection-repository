/**
 * LangDetectorException is a custom exception that wrapping any exception happened on the application
 */
package com.ezz.ld.exceptions;

/**
 * @author Mohamed Ezz
 *
 */
public class LangDetectorException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1596419351961917952L;

	public LangDetectorException() {
		super();
	}

	public LangDetectorException(String message) {
		super(message);
	}

	public LangDetectorException(Throwable cause) {
		super(cause);
	}

}
